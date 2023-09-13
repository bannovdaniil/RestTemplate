package org.example.repository.mapper;

import org.example.model.User;

import java.sql.ResultSet;

public interface UserResultSetMapper {
    User map(ResultSet resultSet);
}
