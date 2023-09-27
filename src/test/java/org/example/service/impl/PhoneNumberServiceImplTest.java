package org.example.service.impl;

import org.example.exception.NotFoundException;
import org.example.model.PhoneNumber;
import org.example.model.Role;
import org.example.repository.PhoneNumberRepository;
import org.example.repository.impl.PhoneNumberRepositoryImpl;
import org.example.service.PhoneNumberService;
import org.example.servlet.dto.PhoneNumberIncomingDto;
import org.example.servlet.dto.PhoneNumberOutGoingDto;
import org.example.servlet.dto.PhoneNumberUpdateDto;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Optional;

class PhoneNumberServiceImplTest {
    private static PhoneNumberService phoneNumberService;
    private static PhoneNumberRepository mockePhoneNumberRepository;
    private static PhoneNumberRepositoryImpl oldInstance;

    private static void setMock(PhoneNumberRepository mock) {
        try {
            Field instance = PhoneNumberRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (PhoneNumberRepositoryImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mockePhoneNumberRepository = Mockito.mock(PhoneNumberRepository.class);
        setMock(mockePhoneNumberRepository);
        phoneNumberService = PhoneNumberServiceImpl.getInstance();
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = PhoneNumberRepositoryImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldInstance);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(mockePhoneNumberRepository);
    }

    @Test
    void save() {
        Long expectedId = 1L;

        PhoneNumberIncomingDto dto = new PhoneNumberIncomingDto("+123 123 1111");
        PhoneNumber phoneNumber = new PhoneNumber(expectedId, "+123 123 1111", null);

        Mockito.doReturn(phoneNumber).when(mockePhoneNumberRepository).save(Mockito.any(PhoneNumber.class));

        PhoneNumberOutGoingDto result = phoneNumberService.save(dto);

        Assertions.assertEquals(expectedId, result.getId());
    }

    @Test
    void update() throws NotFoundException {
        Long expectedId = 1L;

        PhoneNumberUpdateDto dto = new PhoneNumberUpdateDto(expectedId, "+123 123 1111", null);

        Mockito.doReturn(true).when(mockePhoneNumberRepository).exitsById(Mockito.any());

        phoneNumberService.update(dto);

        ArgumentCaptor<PhoneNumber> argumentCaptor = ArgumentCaptor.forClass(PhoneNumber.class);
        Mockito.verify(mockePhoneNumberRepository).update(argumentCaptor.capture());

        PhoneNumber result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedId, result.getId());
    }

    @Test
    void updateNotFound() {
        PhoneNumberUpdateDto dto = new PhoneNumberUpdateDto(1L, "+123 123 1111", null);

        Mockito.doReturn(false).when(mockePhoneNumberRepository).exitsById(Mockito.any());

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    phoneNumberService.update(dto);
                }, "Not found."
        );
        Assertions.assertEquals("PhoneNumber not found.", exception.getMessage());
    }

    @Test
    void findById() throws NotFoundException {
        Long expectedId = 1L;

        Optional<PhoneNumber> role = Optional.of(new PhoneNumber(expectedId, "+123 123 1111", null));

        Mockito.doReturn(true).when(mockePhoneNumberRepository).exitsById(Mockito.any());
        Mockito.doReturn(role).when(mockePhoneNumberRepository).findById(Mockito.anyLong());

        PhoneNumberOutGoingDto dto = phoneNumberService.findById(expectedId);

        Assertions.assertEquals(expectedId, dto.getId());
    }

    @Test
    void findByIdNotFound() {
        Optional<Role> role = Optional.empty();

        Mockito.doReturn(false).when(mockePhoneNumberRepository).exitsById(Mockito.any());

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> {
                    phoneNumberService.findById(1L);
                }, "Not found."
        );
        Assertions.assertEquals("PhoneNumber not found.", exception.getMessage());
    }

    @Test
    void findAll() {
        phoneNumberService.findAll();
        Mockito.verify(mockePhoneNumberRepository).findAll();
    }

    @Test
    void delete() {
        Long expectedId = 100L;

        phoneNumberService.delete(expectedId);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(mockePhoneNumberRepository).deleteById(argumentCaptor.capture());

        Long result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedId, result);
    }
}