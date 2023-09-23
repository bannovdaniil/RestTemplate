package org.example.servlet.mapper;

import org.example.model.User;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.dto.UserUpdateDto;

import java.util.List;

public interface UserDtoMapper {
    User map(UserIncomingDto userIncomingDto);

    User map(UserUpdateDto userIncomingDto);

    UserOutGoingDto map(User user);

    List<UserOutGoingDto> map(List<User> user);

}
