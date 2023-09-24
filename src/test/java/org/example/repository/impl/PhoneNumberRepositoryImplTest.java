package org.example.repository.impl;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.example.model.PhoneNumber;
import org.example.repository.PhoneNumberRepository;
import org.example.util.PropertiesUtil;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;

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

    @Test
    void deleteById() {
    }

    @Test
    void deleteByUserId() {
    }

    @Test
    void existsByNumber() {
    }

    @Test
    void findByNumber() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void exitsById() {
    }

    @Test
    void findAllByUserId() {
    }
}