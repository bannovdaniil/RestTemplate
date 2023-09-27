package org.example.service;

import org.example.exception.NotFoundException;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    UserOutGoingDto save(UserIncomingDto userDto);

    void update(UserUpdateDto userDto) throws NotFoundException;

    UserOutGoingDto findById(Long userId) throws NotFoundException;

    List<UserOutGoingDto> findAll();

    void delete(Long userId) throws NotFoundException;
}
