package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.User;
import org.example.repository.UserEntityRepository;
import org.example.repository.mapper.UserResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class UserEntityRepositoryImpl implements UserEntityRepository {
    private UserResultSetMapper resultSetMapper;
    private ConnectionManager connectionManager;

    @Override
    public User findById(UUID id) {
        // Здесь используем try with resources
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.map(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

}
