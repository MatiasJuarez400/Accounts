package com.matiasjuarez.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFilesReader {

    private static final String SERVER_PROPERTIES = "config/server.properties";
    private static final String H2_PROPERTIES = "config/h2.properties";

    private static PropertyFilesReader instance;

    private PropertyFilesReader() {}

    public static PropertyFilesReader getInstance() {
        if (instance == null) {
            instance = new PropertyFilesReader();
        }

        return instance;
    }

    public Properties getServerProperties() {
        return readProperties(SERVER_PROPERTIES);
    }

    public Properties getH2Properties() {
        return readProperties(H2_PROPERTIES);
    }

    private Properties readProperties(String propertyResource) {
        Properties properties = new Properties();

        try(final InputStream stream = this.getClass().getClassLoader().
                getResourceAsStream(propertyResource)) {

            properties.load(stream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
