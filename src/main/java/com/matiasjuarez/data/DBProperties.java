package com.matiasjuarez.data;

import com.matiasjuarez.utils.PropertyFilesReader;

import java.util.Properties;

public class DBProperties {
    private String dbURL;
    private String username;
    private String password;
    private String driverClassName;
    private String serverPort;

    private static DBProperties instance;

    private DBProperties() {
        Properties properties = PropertyFilesReader.getInstance().getH2Properties();

        this.dbURL = properties.getProperty("url");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
        this.driverClassName = properties.getProperty("driverClassName");
        this.serverPort = properties.getProperty("server.port");
    }

    public static DBProperties getInstance() {
        if (instance == null) {
            instance = new DBProperties();
        }

        return instance;
    }

    public String getDbURL() {
        return dbURL;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getServerPort() {
        return serverPort;
    }
}
