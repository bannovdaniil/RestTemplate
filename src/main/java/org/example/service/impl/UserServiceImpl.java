package org.example.service.impl;

import org.example.exception.NotFoundException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.servlet.dto.UserIncomingDto;
import org.example.servlet.dto.UserOutGoingDto;
import org.example.servlet.dto.UserUpdateDto;
import org.example.servlet.mapper.UserDtoMapper;
import org.example.servlet.mapper.impl.UserDtoMapperImpl;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static final UserDtoMapper userDtoMapper = UserDtoMapperImpl.getInstance();
    private static UserService instance;


    private UserServiceImpl() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private void checkExistUser(Long userId) throws NotFoundException {
        if (!userRepository.exitsById(userId)) {
            throw new NotFoundException("User not found.");
        }
    }

    @Override
    public UserOutGoingDto save(UserIncomingDto userDto) {
        User user = userRepository.save(userDtoMapper.map(userDto));
        return userDtoMapper.map(userRepository.findById(user.getId()).orElse(user));
    }

    @Override
    public void update(UserUpdateDto userDto) throws NotFoundException {
        if (userDto == null || userDto.getId() == null) {
            throw new IllegalArgumentException();
        }
        checkExistUser(userDto.getId());
        userRepository.update(userDtoMapper.map(userDto));
    }

    @Override
    public UserOutGoingDto findById(Long userId) throws NotFoundException {
        checkExistUser(userId);
        User user = userRepository.findById(userId).orElseThrow();
        return userDtoMapper.map(user);
    }

    @Override
    public List<UserOutGoingDto> findAll() {
        List<User> all = userRepository.findAll();
        return userDtoMapper.map(all);
    }

    @Override
    public void delete(Long userId) throws NotFoundException {
        checkExistUser(userId);
        userRepository.deleteById(userId);
    }
}
