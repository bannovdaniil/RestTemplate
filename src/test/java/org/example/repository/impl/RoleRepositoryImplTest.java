package org.example.repository.impl;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.example.util.PropertiesUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

@Testcontainers
@Tag("DockerRequired")
class RoleRepositoryImplTest {
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
    public static RoleRepository roleRepository;
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        container.start();
        roleRepository = RoleRepositoryImpl.getInstance();
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
        String expectedName = "new Role Name";
        Role role = new Role(null, expectedName);
        role = roleRepository.save(role);
        Optional<Role> resultRole = roleRepository.findById(role.getId());

        Assertions.assertTrue(resultRole.isPresent());
        Assertions.assertEquals(expectedName, resultRole.get().getName());
    }

    @Test
    void update() {
        String expectedName = "UPDATE Role Name";

        Role roleForUpdate = roleRepository.findById(3L).get();
        String oldRoleName = roleForUpdate.getName();

        roleForUpdate.setName(expectedName);
        roleRepository.update(roleForUpdate);

        Role role = roleRepository.findById(3L).get();

        Assertions.assertNotEquals(expectedName, oldRoleName);
        Assertions.assertEquals(expectedName, role.getName());
    }

    @DisplayName("Delete by ID")
    @Test
    void deleteById() {
        Boolean expectedValue = true;
        int expectedSize = roleRepository.findAll().size();

        Role tempRole = new Role(null, "Role for delete.");
        tempRole = roleRepository.save(tempRole);

        boolean resultDelete = roleRepository.deleteById(tempRole.getId());
        int roleListAfterSize = roleRepository.findAll().size();

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
        Optional<Role> role = roleRepository.findById(expectedId);
        Assertions.assertEquals(expectedValue, role.isPresent());
        if (role.isPresent()) {
            Assertions.assertEquals(expectedId, role.get().getId());
        }
    }

    @Test
    void findAll() {
        int expectedSize = 5;
        int resultSize = roleRepository.findAll().size();

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
        boolean isRoleExist = roleRepository.exitsById(roleId);

        Assertions.assertEquals(expectedValue, isRoleExist);
    }
}