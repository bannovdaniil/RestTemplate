package org.example.repository.exception;

import java.sql.SQLException;

public class RepositoryException extends RuntimeException {
    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(SQLException e) {
        super(e);
    }
}
