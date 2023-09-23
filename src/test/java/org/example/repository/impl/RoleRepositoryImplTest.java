package org.example.repository.impl;

import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.example.util.PropertiesUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

@Testcontainers
@Tag("DockerRequired")
class RoleRepositoryImplTest {
    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("users_db")
            .withUsername(PropertiesUtil.getProperties("db.username"))
            .withPassword(PropertiesUtil.getProperties("db.password"))
            .withInitScript("sql/schema.sql");

    public static RoleRepository roleRepository;

    @BeforeAll
    static void beforeAll() {
        container.start();
        roleRepository = RoleRepositoryImpl.getInstance();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @Test
    void save() {
        String expectedName = "new Role Name";
        Role role = new Role(null, expectedName);
        role = roleRepository.save(role);
        Optional<Role> expectedRole = roleRepository.findById(role.getId());

        Assertions.assertTrue(expectedRole.isPresent());
        Assertions.assertEquals(expectedName, expectedRole.get().getName());
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
        List<Role> all = roleRepository.findAll();
        Boolean expectedValue = true;
        int expectedSize = 6;

        Role tempRole = new Role(null, "Role for delete.");
        tempRole = roleRepository.save(tempRole);

        boolean resultDelete = roleRepository.deleteById(tempRole.getId());
        List<Role> roleListAfter = all;

        Assertions.assertEquals(expectedValue, resultDelete);
        Assertions.assertEquals(expectedSize, roleListAfter.size());
    }

    @DisplayName("Find by ID")
    @ParameterizedTest
    @CsvSource(value = {
            "1; true; 1",
            "4; true; 4",
            "100; false; 0"
    }, delimiter = ';')
    void findById(Long roleId, Boolean expectedValue, Long expectedId) {
        Optional<Role> role = roleRepository.findById(roleId);
        Assertions.assertEquals(expectedValue, role.isPresent());
        if (role.isPresent()) {
            Assertions.assertEquals(expectedId, role.get().getId());
        }
    }

    @Test
    void findAll() {
        int expectedSize = 6;
        List<Role> roleList = roleRepository.findAll();

        Assertions.assertEquals(expectedSize, roleList.size());
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