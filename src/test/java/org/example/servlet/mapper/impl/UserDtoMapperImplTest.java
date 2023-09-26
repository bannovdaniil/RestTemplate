package org.example.servlet.mapper.impl;

import org.example.model.Department;
import org.example.model.PhoneNumber;
import org.example.model.Role;
import org.example.model.User;
import org.example.servlet.dto.*;
import org.example.servlet.mapper.UserDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class UserDtoMapperImplTest {
    private UserDtoMapper userDtoMapper;

    @BeforeEach
    void setUp() {
        userDtoMapper = UserDtoMapperImpl.getInstance();
    }

    @DisplayName("User map(UserIncomingDto")
    @Test
    void mapIncoming() {
        UserIncomingDto dto = new UserIncomingDto(
                "f1",
                "l2",
                new Role(1L, "role1")
        );
        User result = userDtoMapper.map(dto);

        Assertions.assertNull(result.getId());
        Assertions.assertEquals(dto.getFirstName(), result.getFirstName());
        Assertions.assertEquals(dto.getLastName(), result.getLastName());
        Assertions.assertEquals(dto.getRole().getId(), result.getRole().getId());
    }

    @DisplayName("User map(UserUpdateDto")
    @Test
    void testMapUpdate() {
        UserUpdateDto dto = new UserUpdateDto(
                100L,
                "f1",
                "l2",
                new RoleUpdateDto(1L, "Role update"),
                List.of(new PhoneNumberUpdateDto()),
                List.of(new DepartmentUpdateDto())
        );
        User result = userDtoMapper.map(dto);

        Assertions.assertEquals(dto.getId(), result.getId());
        Assertions.assertEquals(dto.getFirstName(), result.getFirstName());
        Assertions.assertEquals(dto.getLastName(), result.getLastName());
        Assertions.assertEquals(dto.getRole().getId(), result.getRole().getId());
        Assertions.assertEquals(dto.getPhoneNumberList().size(), result.getPhoneNumberList().size());
        Assertions.assertEquals(dto.getDepartmentList().size(), result.getDepartmentList().size());
    }

    @DisplayName("UserOutGoingDto map(User")
    @Test
    void testMapOutgoing() {
        User user = new User(
                100L,
                "f1",
                "l2",
                new Role(1L, "Role #1"),
                List.of(new PhoneNumber(1L, "1324", null)),
                List.of(new Department(1L, "d1", List.of()))
        );
        UserOutGoingDto result = userDtoMapper.map(user);

        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(user.getFirstName(), result.getFirstName());
        Assertions.assertEquals(user.getLastName(), result.getLastName());
        Assertions.assertEquals(user.getRole().getId(), result.getRole().getId());
        Assertions.assertEquals(user.getPhoneNumberList().size(), result.getPhoneNumberList().size());
        Assertions.assertEquals(user.getDepartmentList().size(), result.getDepartmentList().size());
    }

    @DisplayName("List<UserOutGoingDto> map(List<User>")
    @Test
    void testMapList() {
        List<User> userList = List.of(
                new User(
                        100L,
                        "f1",
                        "l2",
                        new Role(1L, "Role #1"),
                        List.of(new PhoneNumber(1L, "1324", null)),
                        List.of(new Department(1L, "d1", List.of()))
                ),
                new User(
                        101L,
                        "f3",
                        "l4",
                        new Role(1L, "Role #1"),
                        List.of(new PhoneNumber(2L, "24242", null)),
                        List.of(new Department(2L, "d2", List.of()))
                )
        );
        int result = userDtoMapper.map(userList).size();
        Assertions.assertEquals(userList.size(), result);
    }
}