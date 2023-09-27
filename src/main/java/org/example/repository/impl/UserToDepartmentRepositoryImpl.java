package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.exception.RepositoryException;
import org.example.model.Department;
import org.example.model.User;
import org.example.model.UserToDepartment;
import org.example.repository.DepartmentRepository;
import org.example.repository.UserRepository;
import org.example.repository.UserToDepartmentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserToDepartmentRepositoryImpl implements UserToDepartmentRepository {
    private static final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private static final DepartmentRepository departmentRepository = DepartmentRepositoryImpl.getInstance();
    private static final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static final String SAVE_SQL = """
            INSERT INTO users_departments (user_id, department_id)
            VALUES (?, ?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE users_departments
            SET user_id = ?,
                department_id = ?
            WHERE users_departments_id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM users_departments
            WHERE users_departments_id = ? ;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT users_departments_id, user_id, department_id FROM users_departments
            WHERE users_departments_id = ?
            LIMIT 1;
            """;
    private static final String FIND_ALL_SQL = """
            SELECT users_departments_id, user_id, department_id FROM users_departments;
            """;
    private static final String FIND_ALL_BY_USERID_SQL = """
            SELECT users_departments_id, user_id, department_id FROM users_departments
            WHERE user_id = ?;
            """;
    private static final String FIND_ALL_BY_DEPARTMENT_ID_SQL = """
            SELECT users_departments_id, user_id, department_id FROM users_departments
            WHERE department_id = ?;
            """;
    private static final String FIND_BY_USERID_AND_DEPARTMENT_ID_SQL = """
            SELECT users_departments_id, user_id, department_id FROM users_departments
            WHERE user_id = ? AND department_id = ?
            LIMIT 1;
            """;
    private static final String DELETE_BY_USERID_SQL = """
            DELETE FROM users_departments
            WHERE user_id = ?;
            """;
    private static final String DELETE_BY_DEPARTMENT_ID_SQL = """
            DELETE FROM users_departments
            WHERE department_id = ?;
            """;
    private static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM users_departments
                        WHERE users_departments_id = ?
                        LIMIT 1);
            """;
    private static UserToDepartmentRepository instance;

    private UserToDepartmentRepositoryImpl() {
    }

    public static synchronized UserToDepartmentRepository getInstance() {
        if (instance == null) {
            instance = new UserToDepartmentRepositoryImpl();
        }
        return instance;
    }

    private static UserToDepartment createUserToDepartament(ResultSet resultSet) throws SQLException {
        UserToDepartment userToDepartment;
        userToDepartment = new UserToDepartment(
                resultSet.getLong("users_departments_id"),
                resultSet.getLong("user_id"),
                resultSet.getLong("department_id")
        );
        return userToDepartment;
    }

    @Override
    public UserToDepartment save(UserToDepartment userToDepartment) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, userToDepartment.getUserId());
            preparedStatement.setLong(2, userToDepartment.getDepartmentId());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                userToDepartment = new UserToDepartment(
                        resultSet.getLong("users_departments_id"),
                        userToDepartment.getUserId(),
                        userToDepartment.getDepartmentId()
                );
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return userToDepartment;
    }

    @Override
    public void update(UserToDepartment userToDepartment) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);) {

            preparedStatement.setLong(1, userToDepartment.getUserId());
            preparedStatement.setLong(2, userToDepartment.getDepartmentId());
            preparedStatement.setLong(3, userToDepartment.getId());

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

            preparedStatement.setLong(1, id);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return deleteResult;
    }

    @Override
    public boolean deleteByUserId(Long userId) {
        boolean deleteResult;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_USERID_SQL);) {

            preparedStatement.setLong(1, userId);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return deleteResult;
    }

    @Override
    public boolean deleteByDepartmentId(Long departmentId) {
        boolean deleteResult;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_DEPARTMENT_ID_SQL);) {

            preparedStatement.setLong(1, departmentId);

            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return deleteResult;
    }

    @Override
    public Optional<UserToDepartment> findById(Long id) {
        UserToDepartment userToDepartment = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userToDepartment = createUserToDepartament(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return Optional.ofNullable(userToDepartment);
    }

    @Override
    public List<UserToDepartment> findAll() {
        List<UserToDepartment> userToDepartmentList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userToDepartmentList.add(createUserToDepartament(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return userToDepartmentList;
    }

    @Override
    public boolean exitsById(Long id) {
        boolean isExists = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXIST_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExists = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return isExists;
    }

    public List<UserToDepartment> findAllByUserId(Long userId) {
        List<UserToDepartment> userToDepartmentList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USERID_SQL)) {

            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userToDepartmentList.add(createUserToDepartament(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return userToDepartmentList;
    }

    @Override
    public List<Department> findDepartmentsByUserId(Long userId) {
        List<Department> departmentList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USERID_SQL)) {

            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long departamentId = resultSet.getLong("department_id");
                Optional<Department> optionalDepartment = departmentRepository.findById(departamentId);
                if (optionalDepartment.isPresent()) {
                    departmentList.add(optionalDepartment.get());
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return departmentList;
    }

    public List<UserToDepartment> findAllByDepartmentId(Long departmentId) {
        List<UserToDepartment> userToDepartmentList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_DEPARTMENT_ID_SQL)) {

            preparedStatement.setLong(1, departmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userToDepartmentList.add(createUserToDepartament(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return userToDepartmentList;
    }

    public List<User> findUsersByDepartmentId(Long departmentId) {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_DEPARTMENT_ID_SQL)) {

            preparedStatement.setLong(1, departmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long userId = resultSet.getLong("user_id");
                Optional<User> optionalUser = userRepository.findById(userId);
                if (optionalUser.isPresent()) {
                    userList.add(optionalUser.get());
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return userList;
    }

    @Override
    public Optional<UserToDepartment> findByUserIdAndDepartmentId(Long userId, Long departmentId) {
        Optional<UserToDepartment> userToDepartment = Optional.empty();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USERID_AND_DEPARTMENT_ID_SQL)) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, departmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userToDepartment = Optional.of(createUserToDepartament(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return userToDepartment;
    }

}
