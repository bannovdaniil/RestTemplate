package org.example.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManagerImpl implements ConnectionManager {
    private Connection connection;
    private static ConnectionManager INSTANCE;

    private ConnectionManagerImpl() {
    }

    public static synchronized ConnectionManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionManagerImpl();
        }
        return INSTANCE;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null) {
            Properties properties = loadProperties();


            this.connection = connection;
        }

        return connection;
    }

    private static Properties loadProperties() {
        var properties = new Properties();
        try (InputStream in = ConnectionManagerImpl.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(in);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
        return properties;
    }

}
