package org.example.service;

import org.example.model.User;

import java.util.UUID;

public interface SimpleService {
    User save(User user);

    User findById(UUID uuid);
}
