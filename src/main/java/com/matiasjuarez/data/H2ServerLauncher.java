package com.matiasjuarez.data;

import org.h2.tools.Server;

import java.sql.SQLException;

public class H2ServerLauncher {
    private Server webServer;

    public void launch() throws SQLException {
        webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort",
                DBProperties.getInstance().getServerPort()).start();
    }

    public void stop() {
        if (webServer != null) {
            webServer.stop();
        }
    }
}
