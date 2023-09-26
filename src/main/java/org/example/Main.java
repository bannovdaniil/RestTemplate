package org.example;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.util.InitSqlScheme;

public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
        InitSqlScheme.initSqlScheme(connectionManager);
        InitSqlScheme.initSqlData(connectionManager);

    }
}