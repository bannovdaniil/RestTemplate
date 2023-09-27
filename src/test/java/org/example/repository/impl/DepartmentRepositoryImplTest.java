package org.example.repository.impl;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.example.model.Department;
import org.example.repository.DepartmentRepository;
import org.example.util.PropertiesUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

@Testcontainers
@Tag("DockerRequired")
class DepartmentRepositoryImplTest {
    private static final String INIT_SQL = "sql/schema.sql";
    public static DepartmentRepository departmentRepository;
    private static int containerPort = 5432;
    private static int localPort = 5432;
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
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        container.start();
        departmentRepository = DepartmentRepositoryImpl.getInstance();
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
        String expectedName = "new Department Yo!";
        Department department = new Department(
                null,
                expectedName,
                null
        );
        department = departmentRepository.save(department);
        Optional<Department> resultDepartment = departmentRepository.findById(department.getId());

        Assertions.assertTrue(resultDepartment.isPresent());
        Assertions.assertEquals(expectedName, resultDepartment.get().getName());

    }

    @Test
    void update() {
        String expectedName = "Update department name";

        Department department = departmentRepository.findById(2L).get();
        String oldName = department.getName();
        int expectedSizeUserList = department.getUserList().size();
        department.setName(expectedName);
        departmentRepository.update(department);

        Department resultDepartment = departmentRepository.findById(2L).get();
        int resultSizeUserList = resultDepartment.getUserList().size();

        Assertions.assertNotEquals(expectedName, oldName);
        Assertions.assertEquals(expectedName, resultDepartment.getName());
        Assertions.assertEquals(expectedSizeUserList, resultSizeUserList);
    }

    @Test
    void deleteById() {
        Boolean expectedValue = true;
        int expectedSize = departmentRepository.findAll().size();

        Department tempDepartment = new Department(null, "New department", List.of());
        tempDepartment = departmentRepository.save(tempDepartment);

        int resultSizeBefore = departmentRepository.findAll().size();
        Assertions.assertNotEquals(expectedSize, resultSizeBefore);

        boolean resultDelete = departmentRepository.deleteById(tempDepartment.getId());
        int resultSizeAfter = departmentRepository.findAll().size();

        Assertions.assertEquals(expectedValue, resultDelete);
        Assertions.assertEquals(expectedSize, resultSizeAfter);

    }

    @DisplayName("Find by ID")
    @ParameterizedTest
    @CsvSource(value = {
            "1, true",
            "4, true",
            "1000, false"
    })
    void findById(Long expectedId, Boolean expectedValue) {
        Optional<Department> department = departmentRepository.findById(expectedId);

        Assertions.assertEquals(expectedValue, department.isPresent());
        if (department.isPresent()) {
            Assertions.assertEquals(expectedId, department.get().getId());
        }
    }

    @Test
    void findAll() {
        int expectedSize = 4;
        int resultSize = departmentRepository.findAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }

    @DisplayName("Exist by ID")
    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "4; true",
            "100; false"
    }, delimiter = ';')
    void exitsById(Long departmentId, Boolean expectedValue) {
        boolean isRoleExist = departmentRepository.exitsById(departmentId);

        Assertions.assertEquals(expectedValue, isRoleExist);
    }
}