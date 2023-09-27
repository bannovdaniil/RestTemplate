package org.example.repository.impl;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.example.model.Department;
import org.example.model.PhoneNumber;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
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

class UserRepositoryImplTest {
    private static final String INIT_SQL = "sql/schema.sql";
    private static final int containerPort = 5432;
    private static final int localPort = 5432;
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
    public static UserRepository userRepository;
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        container.start();
        userRepository = UserRepositoryImpl.getInstance();
        jdbcDatabaseDelegate = new JdbcDatabaseDelegate(container, "");
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    void setUp() {
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, INIT_SQL);
    }

    @Test
    void save() {
        String expectedFirstname = "new Firstname";
        String expectedLastname = "new Lastname";

        User user = new User(
                null,
                expectedFirstname,
                expectedLastname,
                null,
                null,
                null);
        user = userRepository.save(user);
        Optional<User> resultUser = userRepository.findById(user.getId());

        Assertions.assertTrue(resultUser.isPresent());
        Assertions.assertEquals(expectedFirstname, resultUser.get().getFirstName());
        Assertions.assertEquals(expectedLastname, resultUser.get().getLastName());
    }

    @Test
    void update() {
        String expectedFirstname = "UPDATE Firstname";
        String expectedLastname = "UPDATE Lastname";
        Long expectedRoleId = 1L;

        User userForUpdate = userRepository.findById(3L).get();

        List<Department> departmentList = userForUpdate.getDepartmentList();
        int phoneListSize = userForUpdate.getPhoneNumberList().size();
        int departmentListSize = userForUpdate.getDepartmentList().size();
        Role oldRole = userForUpdate.getRole();

        Assertions.assertNotEquals(expectedRoleId, userForUpdate.getRole().getId());
        Assertions.assertNotEquals(expectedFirstname, userForUpdate.getFirstName());
        Assertions.assertNotEquals(expectedLastname, userForUpdate.getLastName());

        userForUpdate.setFirstName(expectedFirstname);
        userForUpdate.setLastName(expectedLastname);
        userRepository.update(userForUpdate);

        User resultUser = userRepository.findById(3L).get();

        Assertions.assertEquals(expectedFirstname, resultUser.getFirstName());
        Assertions.assertEquals(expectedLastname, resultUser.getLastName());

        Assertions.assertEquals(phoneListSize, resultUser.getPhoneNumberList().size());
        Assertions.assertEquals(departmentListSize, resultUser.getDepartmentList().size());
        Assertions.assertEquals(oldRole.getId(), resultUser.getRole().getId());

        userForUpdate.setPhoneNumberList(List.of());
        userForUpdate.setDepartmentList(List.of());
        userForUpdate.setRole(new Role(expectedRoleId, null));
        userRepository.update(userForUpdate);
        resultUser = userRepository.findById(3L).get();

        Assertions.assertEquals(0, resultUser.getPhoneNumberList().size());
        Assertions.assertEquals(0, resultUser.getDepartmentList().size());
        Assertions.assertEquals(expectedRoleId, resultUser.getRole().getId());

        departmentList.add(new Department(3L, null, null));
        departmentList.add(new Department(4L, null, null));
        userForUpdate.setDepartmentList(departmentList);
        userRepository.update(userForUpdate);
        resultUser = userRepository.findById(3L).get();

        Assertions.assertEquals(3, resultUser.getDepartmentList().size());

        departmentList.remove(2);
        userForUpdate.setDepartmentList(departmentList);
        userRepository.update(userForUpdate);
        resultUser = userRepository.findById(3L).get();

        Assertions.assertEquals(2, resultUser.getDepartmentList().size());

        userForUpdate.setPhoneNumberList(List.of(
                new PhoneNumber(null, "+4 new phone", null),
                new PhoneNumber(null, "+1(123)123 2222", null)));
        userForUpdate.setDepartmentList(List.of(new Department(1L, null, null)));

        userRepository.update(userForUpdate);
        resultUser = userRepository.findById(3L).get();

        Assertions.assertEquals(1, resultUser.getPhoneNumberList().size());
        Assertions.assertEquals(1, resultUser.getDepartmentList().size());
    }

    @Test
    void deleteById() {
        Boolean expectedValue = true;
        int expectedSize = userRepository.findAll().size();

        User tempUser = new User(
                null,
                "User for delete Firstname.",
                "User for delete Lastname.",
                null,
                null,
                null
        );
        tempUser = userRepository.save(tempUser);

        boolean resultDelete = userRepository.deleteById(tempUser.getId());
        int roleListAfterSize = userRepository.findAll().size();

        Assertions.assertEquals(expectedValue, resultDelete);
        Assertions.assertEquals(expectedSize, roleListAfterSize);
    }

    @DisplayName("Find by ID")
    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "4; true",
            "100; false"
    }, delimiter = ';')
    void findById(Long expectedId, Boolean expectedValue) {
        Optional<User> user = userRepository.findById(expectedId);
        Assertions.assertEquals(expectedValue, user.isPresent());
        user.ifPresent(value -> Assertions.assertEquals(expectedId, value.getId()));
    }

    @Test
    void findAll() {
        int expectedSize = 7;
        int resultSize = userRepository.findAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }

    @DisplayName("Exist by ID")
    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "4; true",
            "100; false"
    }, delimiter = ';')
    void exitsById(Long roleId, Boolean expectedValue) {
        boolean isUserExist = userRepository.exitsById(roleId);

        Assertions.assertEquals(expectedValue, isUserExist);
    }
}