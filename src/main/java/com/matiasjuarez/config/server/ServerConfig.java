package com.matiasjuarez.config.server;

import com.matiasjuarez.utils.PropertyFilesReader;

import java.util.Properties;

public class ServerConfig {
    private static ServerConfig serverConfig;

    private Integer serverPort;
    private String serverBasePath;

    private ServerConfig() {
        Properties properties = PropertyFilesReader.getInstance().getServerProperties();
        serverPort = Integer.parseInt(properties.getProperty("server.port"));
        serverBasePath = properties.getProperty("server.base.path");
    }

    public static ServerConfig getInstance() {
        if (serverConfig == null) {
            serverConfig = new ServerConfig();
        }

        return serverConfig;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public String getServerBasePath() {
        return serverBasePath;
    }
}
