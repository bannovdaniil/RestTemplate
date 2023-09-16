package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.User;
import org.example.repository.UserEntityRepository;
import org.example.repository.exception.NotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class UserEntityRepositoryImpl implements UserEntityRepository {
    private ConnectionManager connectionManager;

    @Override
    public User findById(UUID id) throws NotFoundException {
        User user = null;

        String sqlGetUser = """
                SELECT u.user_uuid, u.firstname, u.lastname, u.role_id
                 FROM users as u
                  WHERE user_uuid = ? LIMIT 1;
                """;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlGetUser)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        (UUID) resultSet.getObject("user_uuid"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        null,
                        null,
                        null
                );
                user.setRole(null);
                user.setPhoneNumberList(null);
                user.setDepartmentList(null);
            } else {
                throw new NotFoundException("User not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public boolean deleteById(UUID id) {
        boolean deleteResult = true;

        String sqlGetUser = "DELETE FROM users WHERE user_uuid = ?;";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlGetUser)) {

            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                deleteResult = false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return deleteResult;
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
