package org.example.db;

import org.example.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManagerImpl implements ConnectionManager {
    private final static String DRIVER_CLASS_KEY = "db.driver-class-name";
    private final static String URL_KEY = "db.url";
    private final static String USERNAME_KEY = "db.username";
    private final static String PASSWORD_KEY = "db.password";
    private static ConnectionManager INSTANCE;

    private ConnectionManagerImpl() {
    }

    public static synchronized ConnectionManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionManagerImpl();
            loadDriver(PropertiesUtil.getProperties(DRIVER_CLASS_KEY));
        }
        return INSTANCE;
    }

    private static void loadDriver(String driverClass) {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.getProperties(URL_KEY),
                PropertiesUtil.getProperties(USERNAME_KEY),
                PropertiesUtil.getProperties(PASSWORD_KEY)
        );
    }

}
