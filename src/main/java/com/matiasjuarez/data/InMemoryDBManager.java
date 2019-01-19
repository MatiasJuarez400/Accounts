package com.matiasjuarez.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InMemoryDBManager {
    private static InMemoryDBManager inMemoryDBsManager;

    private DBProperties dbProperties;

    private InMemoryDBManager() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.dbProperties = DBProperties.getInstance();
    }

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(dbProperties.getDbURL(),
                    dbProperties.getUsername(), dbProperties.getPassword());

            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static InMemoryDBManager getInstance() {
        if (inMemoryDBsManager == null) {
            inMemoryDBsManager = new InMemoryDBManager();
        }

        return inMemoryDBsManager;
    }
}
