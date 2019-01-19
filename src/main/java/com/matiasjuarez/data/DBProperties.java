package com.matiasjuarez.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBProperties {
    private String dbURL;
    private String username;
    private String password;

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

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
