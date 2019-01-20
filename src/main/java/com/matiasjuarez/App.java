package com.matiasjuarez;

import com.matiasjuarez.money.resources.CurrencyResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        App app = new App();

        app.jettying();
    }

    private void jettying() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8090);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*"
        );

        jerseyServlet.setInitParameter(
            "jersey.config.server.provider.classnames",
                CurrencyResource.class.getCanonicalName()
        );

        try {
            jettyServer.start();
            jettyServer.join();
        } catch(Exception ex) {
            jettyServer.destroy();
        }
    }
}
