package com.matiasjuarez.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBProperties {
    private String dbURL;
    private String username;
    private String password;
    private String driverClassName;

    private static final String PROPERTIES_SOURCE = "h2.properties";

    private static DBProperties instance;

    private DBProperties() {
        Properties properties = new Properties();

        try(final InputStream stream = this.getClass().getClassLoader().
                getResourceAsStream(PROPERTIES_SOURCE)) {

            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.dbURL = properties.getProperty("url");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
        this.driverClassName = properties.getProperty("driverClassName");
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
}
