package org.example.service.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.User;
import org.example.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private static UserService INSTANCE;


    private UserServiceImpl() {
    }

    public static synchronized UserService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public User save(User user) {
        try {
            Connection connection = connectionManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public User findById(UUID uuid) {
        return null;
    }
}
