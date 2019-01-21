package com.matiasjuarez.config.server;

import com.matiasjuarez.config.injection.AppResourceConfig;
import com.matiasjuarez.api.currency.CurrencyResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class ServerLauncher {
    private Server server;

    public void launch() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(ServerConfig.getInstance().getServerBasePath());

        ServletContainer servletContainer = new ServletContainer(new AppResourceConfig());
        ServletHolder servletHolder = new ServletHolder(servletContainer);

        server = new Server(ServerConfig.getInstance().getServerPort());
        server.setHandler(context);

        context.addServlet(servletHolder, "/*");

//        ServletHolder jerseyServlet = context.addServlet(
//                org.glassfish.jersey.servlet.ServletContainer.class, "/*"
//        );

        servletHolder.setInitParameter(
                "jersey.config.server.provider.classnames",
                CurrencyResource.class.getCanonicalName()
        );

        try {
            server.start();
            server.join();
        } catch(Exception ex) {
            stop();
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }
    }
}
