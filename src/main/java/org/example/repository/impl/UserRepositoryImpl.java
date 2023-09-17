package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.PhoneNumber;
import org.example.model.User;
import org.example.model.UserToDepartment;
import org.example.repository.PhoneNumberRepository;
import org.example.repository.UserRepository;
import org.example.repository.UserToDepartmentRepository;
import org.example.repository.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private static UserRepository instance;
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private final UserToDepartmentRepository userToDepartmentRepository = UserToDepartmentRepositoryImpl.getInstance();
    private final PhoneNumberRepository phoneNumberRepository = PhoneNumberRepositoryImpl.getInstance();

    private UserRepositoryImpl() {
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    private static final String SAVE_SQL = """
            INSERT INTO users (user_firstname, user_lastname, role_id)
            VALUES (?, ? ,?) ;
            """;

    private static final String UPDATE_SQL = """
            UPDATE users
            SET user_firstname = ?,
                user_lastname = ?,
                role_id =?
            WHERE user_id = ?  ;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE user_id = ? ;
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT user_id, user_firstname, user_lastname, role_id FROM users
            WHERE user_id = ?
            LIMIT 1;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT user_id, user_firstname, user_lastname, role_id FROM users;
            """;

    @Override
    public User save(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setLong(3, user.getRoleId());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("role_id"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return user;
    }

    @Override
    public void update(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setLong(3, user.getRoleId());
            preparedStatement.setLong(4, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleteResult;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);) {

            userToDepartmentRepository.deleteByUserId(id);

            preparedStatement.setLong(1, id);
            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return deleteResult;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUser(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(createUser(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return userList;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        List<PhoneNumber> phoneNumberList = phoneNumberRepository.findAllByUserId(userId);
        List<Long> userDepartmentIdList = userToDepartmentRepository
                .findAll()
                .stream()
                .map(UserToDepartment::getDepartmentId)
                .toList();

        return new User(
                userId,
                resultSet.getString("user_firstname"),
                resultSet.getString("user_lastname"),
                resultSet.getLong("role_id"),
                phoneNumberList,
                userDepartmentIdList
        );
    }
}
