package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.PhoneNumber;
import org.example.model.Role;
import org.example.model.User;
import org.example.model.UserToDepartment;
import org.example.repository.PhoneNumberRepository;
import org.example.repository.RoleRepository;
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
    private final RoleRepository roleRepository = RoleRepositoryImpl.getInstance();

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

    /**
     * Сохранят в базу сущность пользователя,
     * 1. сохраняем самого пользователя,
     * 2. сохраняем его роль
     * 3. сохраняем список телефонов.
     * 4. сохраняем список отделов.
     *
     * @param user
     * @return
     */
    @Override
    public User save(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            if (user.getRole() == null) {
                preparedStatement.setNull(3, Types.NULL);
            } else {
                preparedStatement.setLong(3, user.getRole().getId());
            }
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("user_id"));
            }
            savePhoneNumberList(user);
            saveDepartmentList(user);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return user;
    }


    /**
     * 1. Проверяем список на пустоту
     * 1.1 если пустой то удаляем все записи из базы которые == userId.
     * 1.2 получаем все записи которые уже есть в базе
     * 1.3 сверяем то что есть, добавляем, обновляем, или удаляем.
     *
     * @param user
     */
    private void saveDepartmentList(User user) {
        if (user.getDepartmentIdList() != null && !user.getDepartmentIdList().isEmpty()) {
            List<Long> departmentIdList = new ArrayList<>(user.getDepartmentIdList());
            List<UserToDepartment> existsDepartamentList = userToDepartmentRepository.findAllByUserId(user.getId());
            for (UserToDepartment userToDepartment : existsDepartamentList) {
                if (!departmentIdList.contains(userToDepartment.getDepartmentId())) {
                    userToDepartmentRepository.deleteById(userToDepartment.getId());
                }
                departmentIdList.remove(userToDepartment.getUserId());
            }
            for (Long departmentId : departmentIdList) {
                UserToDepartment userToDepartment = new UserToDepartment(
                        null,
                        user.getId(),
                        departmentId
                );
                userToDepartmentRepository.save(userToDepartment);
            }

        } else {
            userToDepartmentRepository.deleteByUserId(user.getId());
        }
    }

    /**
     * 1. Проверяем список на пустоту
     * 1.1 если пустой то удаляем все записи из базы которые == userId.
     * 1.2 получаем все записи которые уже есть в базе
     * 1.3 сверяем то что есть, добавляем, обновляем, или удаляем.
     *
     * @param user
     */
    private void savePhoneNumberList(User user) {
        if (user.getPhoneNumberList() != null && !user.getPhoneNumberList().isEmpty()) {
            List<PhoneNumber> phoneNumberList = new ArrayList<>(user.getPhoneNumberList());
            List<Long> existsPhoneNumberIdList = phoneNumberRepository.findAllByUserId(user.getId())
                    .stream()
                    .map(PhoneNumber::getId)
                    .toList();

            for (int i = 0; i < phoneNumberList.size(); i++) {
                PhoneNumber phoneNumber = phoneNumberList.get(i);
                phoneNumber.setUserId(user.getId());
                if (existsPhoneNumberIdList.contains(phoneNumber.getId())) {
                    phoneNumberRepository.update(phoneNumber);
                } else {
                    phoneNumberRepository.save(phoneNumber);
                }
                phoneNumberList.set(i, null);
            }
            phoneNumberList.removeAll(null);
            phoneNumberList
                    .stream()
                    .forEach(phoneNumber -> {
                        phoneNumber.setUserId(user.getId());
                        phoneNumberRepository.save(phoneNumber);
                    });
        } else {
            phoneNumberRepository.deleteByUserId(user.getId());
        }

    }

    @Override
    public void update(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            if (user.getRole() == null) {
                preparedStatement.setNull(3, Types.NULL);
            } else {
                preparedStatement.setLong(3, user.getRole().getId());
            }
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
            phoneNumberRepository.deleteByUserId(id);

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
        Role role = roleRepository.findById(resultSet.getLong("role_id")).orElse(null);
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
                role,
                phoneNumberList,
                userDepartmentIdList
        );
    }
}
