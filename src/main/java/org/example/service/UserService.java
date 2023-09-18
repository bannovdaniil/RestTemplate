package org.example.service;

import org.example.model.User;
import org.example.repository.exception.NotFoundException;

import java.util.List;

public interface UserService {
    User save(User user);

    void update(User user);

    User findById(Long userId) throws NotFoundException;

    List<User> findAll();

    boolean delete(Long userId);
}
