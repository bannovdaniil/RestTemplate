package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.PhoneNumber;
import org.example.repository.PhoneNumberRepository;
import org.example.repository.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhoneNumberRepositoryImpl implements PhoneNumberRepository {
    private static PhoneNumberRepository instance;
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    private PhoneNumberRepositoryImpl() {
    }

    public static synchronized PhoneNumberRepository getInstance() {
        if (instance == null) {
            instance = new PhoneNumberRepositoryImpl();
        }
        return instance;
    }

    private static final String SAVE_SQL = """
            INSERT INTO phonenumbers (phonenumber_number, user_id)
            VALUES (?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE phonenumbers
            SET phonenumber_number = ?,
                user_id = ?
            WHERE phonenumber_id = ?;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM phonenumbers
            WHERE phonenumber_id = ?;
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT phonenumber_id, phonenumber_number, user_id FROM phonenumbers
            WHERE phonenumber_id = ?
            LIMIT 1;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT phonenumber_id, phonenumber_number, user_id FROM phonenumbers;
            """;

    @Override
    public PhoneNumber save(PhoneNumber phoneNumber) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, phoneNumber.getNumber());
            if (phoneNumber.getUserId() == null) {
                preparedStatement.setNull(2, Types.NULL);
            } else {
                preparedStatement.setLong(2, phoneNumber.getUserId());
            }
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                phoneNumber.setId(resultSet.getLong("phonenumber_id"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return phoneNumber;
    }

    @Override
    public void update(PhoneNumber phoneNumber) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);) {

            preparedStatement.setString(1, phoneNumber.getNumber());
            preparedStatement.setLong(2, phoneNumber.getId());
            if (phoneNumber.getUserId() == null) {
                preparedStatement.setNull(3, Types.NULL);
            } else {
                preparedStatement.setLong(3, phoneNumber.getUserId());
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleteResult = true;
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
    public Optional<PhoneNumber> findById(Long id) {
        PhoneNumber phoneNumber = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                phoneNumber = createPhoneNumber(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return Optional.ofNullable(phoneNumber);
    }

    private static PhoneNumber createPhoneNumber(ResultSet resultSet) throws SQLException {
        PhoneNumber phoneNumber;
        phoneNumber = new PhoneNumber(
                resultSet.getLong("phonenumber_id"),
                resultSet.getString("phonenumber_number"),
                resultSet.getLong("user_id"));
        return phoneNumber;
    }

    @Override
    public List<PhoneNumber> findAll() {
        List<PhoneNumber> phoneNumberList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                phoneNumberList.add(createPhoneNumber(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return phoneNumberList;
    }
}