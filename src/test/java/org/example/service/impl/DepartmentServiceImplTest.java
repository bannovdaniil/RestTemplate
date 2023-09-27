package org.example.service.impl;

import org.example.exception.NotFoundException;
import org.example.model.Department;
import org.example.model.UserToDepartment;
import org.example.repository.DepartmentRepository;
import org.example.repository.UserRepository;
import org.example.repository.UserToDepartmentRepository;
import org.example.repository.impl.DepartmentRepositoryImpl;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.repository.impl.UserToDepartmentRepositoryImpl;
import org.example.service.DepartmentService;
import org.example.servlet.dto.DepartmentIncomingDto;
import org.example.servlet.dto.DepartmentOutGoingDto;
import org.example.servlet.dto.DepartmentUpdateDto;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

class DepartmentServiceImplTest {
    private static DepartmentService departmentService;
    private static DepartmentRepository mockDepartmentRepository;
    private static UserRepository mockUserRepository;
    private static UserToDepartmentRepository mockUserToDepartmentRepository;
    private static DepartmentRepositoryImpl oldDepartmentInstance;
    private static UserRepositoryImpl oldUserInstance;
    private static UserToDepartmentRepositoryImpl oldLinkInstance;

    private static void setMock(DepartmentRepository mock) {
        try {
            Field instance = DepartmentRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldDepartmentInstance = (DepartmentRepositoryImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setMock(UserRepository mock) {
        try {
            Field instance = UserRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldUserInstance = (UserRepositoryImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setMock(UserToDepartmentRepository mock) {
        try {
            Field instance = UserToDepartmentRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldLinkInstance = (UserToDepartmentRepositoryImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mockDepartmentRepository = Mockito.mock(DepartmentRepository.class);
        setMock(mockDepartmentRepository);
        mockUserRepository = Mockito.mock(UserRepository.class);
        setMock(mockUserRepository);
        mockUserToDepartmentRepository = Mockito.mock(UserToDepartmentRepository.class);
        setMock(mockUserToDepartmentRepository);

        departmentService = DepartmentServiceImpl.getInstance();
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = DepartmentRepositoryImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldDepartmentInstance);

        instance = UserRepositoryImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldUserInstance);

        instance = UserToDepartmentRepositoryImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldLinkInstance);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(mockDepartmentRepository);
    }

    @Test
    void save() {
        Long expectedId = 1L;

        DepartmentIncomingDto dto = new DepartmentIncomingDto("department #2");
        Department department = new Department(expectedId, "department #10", List.of());

        Mockito.doReturn(department).when(mockDepartmentRepository).save(Mockito.any(Department.class));

        DepartmentOutGoingDto result = departmentService.save(dto);

        Assertions.assertEquals(expectedId, result.getId());
    }

    @Test
    void update() throws NotFoundException {
        Long expectedId = 1L;

        DepartmentUpdateDto dto = new DepartmentUpdateDto(expectedId, "department update #1");

        Mockito.doReturn(true).when(mockDepartmentRepository).exitsById(Mockito.any());

        departmentService.update(dto);

        ArgumentCaptor<Department> argumentCaptor = ArgumentCaptor.forClass(Department.class);
        Mockito.verify(mockDepartmentRepository).update(argumentCaptor.capture());

        Department result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedId, result.getId());
    }

    @Test
    void updateNotFound() {
        DepartmentUpdateDto dto = new DepartmentUpdateDto(1L, "department update #1");

        Mockito.doReturn(false).when(mockDepartmentRepository).exitsById(Mockito.any());

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    departmentService.update(dto);
                }, "Not found."
        );
        Assertions.assertEquals("Department not found.", exception.getMessage());
    }

    @Test
    void findById() throws NotFoundException {
        Long expectedId = 1L;

        Optional<Department> department = Optional.of(new Department(expectedId, "department found #1", List.of()));

        Mockito.doReturn(true).when(mockDepartmentRepository).exitsById(Mockito.any());
        Mockito.doReturn(department).when(mockDepartmentRepository).findById(Mockito.anyLong());

        DepartmentOutGoingDto dto = departmentService.findById(expectedId);

        Assertions.assertEquals(expectedId, dto.getId());
    }

    @Test
    void findByIdNotFound() {
        Optional<Department> department = Optional.empty();

        Mockito.doReturn(false).when(mockDepartmentRepository).exitsById(Mockito.any());

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    departmentService.findById(1L);
                }, "Not found."
        );
        Assertions.assertEquals("Department not found.", exception.getMessage());
    }

    @Test
    void findAll() {
        departmentService.findAll();
        Mockito.verify(mockDepartmentRepository).findAll();
    }

    @Test
    void delete() throws NotFoundException {
        Long expectedId = 100L;

        Mockito.doReturn(true).when(mockDepartmentRepository).exitsById(Mockito.any());
        departmentService.delete(expectedId);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(mockDepartmentRepository).deleteById(argumentCaptor.capture());

        Long result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedId, result);
    }

    @Test
    void deleteUserFromDepartment() throws NotFoundException {
        Long expectedId = 100L;
        Optional<UserToDepartment> link = Optional.of(new UserToDepartment(expectedId, 1L, 2L));

        Mockito.doReturn(true).when(mockUserRepository).exitsById(Mockito.any());
        Mockito.doReturn(true).when(mockDepartmentRepository).exitsById(Mockito.any());
        Mockito.doReturn(link).when(mockUserToDepartmentRepository).findByUserIdAndDepartmentId(Mockito.anyLong(), Mockito.anyLong());

        departmentService.deleteUserFromDepartment(1L, 1l);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(mockUserToDepartmentRepository).deleteById(argumentCaptor.capture());
        Long result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedId, result);
    }

    @Test
    void addUserToDepartment() throws NotFoundException {
        Long expectedUserId = 100L;
        Long expectedDepartmentId = 500L;

        Mockito.doReturn(true).when(mockUserRepository).exitsById(Mockito.any());
        Mockito.doReturn(true).when(mockDepartmentRepository).exitsById(Mockito.any());

        departmentService.addUserToDepartment(expectedDepartmentId, expectedUserId);

        ArgumentCaptor<UserToDepartment> argumentCaptor = ArgumentCaptor.forClass(UserToDepartment.class);
        Mockito.verify(mockUserToDepartmentRepository).save(argumentCaptor.capture());
        UserToDepartment result = argumentCaptor.getValue();

        Assertions.assertEquals(expectedUserId, result.getUserId());
        Assertions.assertEquals(expectedDepartmentId, result.getDepartmentId());
    }
}