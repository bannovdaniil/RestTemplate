package org.example.repository.impl;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.example.model.PhoneNumber;
import org.example.repository.PhoneNumberRepository;
import org.example.util.PropertiesUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;
import java.util.Optional;

class PhoneNumberRepositoryImplTest {
    private static final String INIT_SQL = "sql/schema.sql";
    private static int containerPort = 5432;
    private static int localPort = 5432;
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("users_db")
            .withUsername(PropertiesUtil.getProperties("db.username"))
            .withPassword(PropertiesUtil.getProperties("db.password"))
            .withExposedPorts(containerPort)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
            ))
            .withInitScript(INIT_SQL);

    public static PhoneNumberRepository phoneNumberRepository;

    @BeforeAll
    static void beforeAll() {
        container.start();
        phoneNumberRepository = PhoneNumberRepositoryImpl.getInstance();
        jdbcDatabaseDelegate = new JdbcDatabaseDelegate(container, "");
    }

    @BeforeEach
    void setUp() {
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, INIT_SQL);
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @Test
    void save() {
        String expectedNumber = "+3 (123) 123 4321";
        PhoneNumber phoneNumber = new PhoneNumber(
                null,
                expectedNumber,
                null
        );
        phoneNumber = phoneNumberRepository.save(phoneNumber);
        Optional<PhoneNumber> expectedPhone = phoneNumberRepository.findById(phoneNumber.getId());

        Assertions.assertTrue(expectedPhone.isPresent());
        Assertions.assertEquals(expectedNumber, expectedPhone.get().getNumber());

    }

    @Test
    void update() {
        String expectedNumber = "+3 (321) 321 4321";

        PhoneNumber phoneNumberUpdate = phoneNumberRepository.findById(3L).get();
        String oldPhoneNumber = phoneNumberUpdate.getNumber();

        phoneNumberUpdate.setNumber(expectedNumber);
        phoneNumberRepository.update(phoneNumberUpdate);

        PhoneNumber number = phoneNumberRepository.findById(3L).get();

        Assertions.assertNotEquals(expectedNumber, oldPhoneNumber);
        Assertions.assertEquals(expectedNumber, number.getNumber());
    }

    @DisplayName("Delete by ID")
    @Test
    void deleteById() {
        Boolean expectedValue = true;
        int expectedSize = phoneNumberRepository.findAll().size();

        PhoneNumber tempNumber = new PhoneNumber(null, "+(temp) number", null);
        tempNumber = phoneNumberRepository.save(tempNumber);

        boolean resultDelete = phoneNumberRepository.deleteById(tempNumber.getId());
        List<PhoneNumber> phoneNumberListAfter = phoneNumberRepository.findAll();

        Assertions.assertEquals(expectedValue, resultDelete);
        Assertions.assertEquals(expectedSize, phoneNumberListAfter.size());

    }

    @DisplayName("Delete by ID")
    @Test
    void deleteByUserId() {
        Boolean expectedValue = true;
        int expectedSize = phoneNumberRepository.findAll().size() - phoneNumberRepository.findAllByUserId(1L).size();

        boolean resultDelete = phoneNumberRepository.deleteByUserId(1L);

        int resultSize = phoneNumberRepository.findAll().size();
        Assertions.assertEquals(expectedValue, resultDelete);
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @DisplayName("Check exist by Phone number.")
    @ParameterizedTest
    @CsvSource(value = {
            "'+1(123)123 5555',true",
            "'not exits number', false"
    })
    void existsByNumber(String number, Boolean expectedValue) {
        boolean isExist = phoneNumberRepository.existsByNumber(number);

        Assertions.assertEquals(expectedValue, isExist);
    }

    @DisplayName("Find by Phone number.")
    @ParameterizedTest
    @CsvSource(value = {
            "'+1(123)123 5555',true",
            "'not exits number', false"
    })
    void findByNumber(String findNumber, Boolean expectedValue) {
        Optional<PhoneNumber> phoneNumber = phoneNumberRepository.findByNumber(findNumber);

        Assertions.assertEquals(expectedValue, phoneNumber.isPresent());
        if (phoneNumber.isPresent()) {
            Assertions.assertEquals(findNumber, phoneNumber.get().getNumber());
        }
    }

    @DisplayName("Find by ID")
    @ParameterizedTest
    @CsvSource(value = {
            "1, true",
            "4, true",
            "1000, false"
    })
    void findById(Long expectedId, Boolean expectedValue) {
        Optional<PhoneNumber> phoneNumber = phoneNumberRepository.findById(expectedId);

        Assertions.assertEquals(expectedValue, phoneNumber.isPresent());
        if (phoneNumber.isPresent()) {
            Assertions.assertEquals(expectedId, phoneNumber.get().getId());
        }
    }

    @Test
    void findAll() {
        int expectedSize = 9;
        int resultSize = phoneNumberRepository.findAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }

    @DisplayName("Exist by ID")
    @ParameterizedTest
    @CsvSource(value = {
            "1, true",
            "4, true",
            "1000, false"
    })
    void exitsById(Long expectedId, Boolean expectedValue) {
        Boolean resultValue = phoneNumberRepository.exitsById(expectedId);

        Assertions.assertEquals(expectedValue, resultValue);
    }

    @DisplayName("Find by UserId")
    @ParameterizedTest
    @CsvSource(value = {
            "1, 2",
            "2, 2",
            "3, 1",
            "1000, 0"
    })
    void findAllByUserId(Long userId, int expectedSize) {
        int resultSize = phoneNumberRepository.findAllByUserId(userId).size();

        Assertions.assertEquals(expectedSize, resultSize);
    }
}