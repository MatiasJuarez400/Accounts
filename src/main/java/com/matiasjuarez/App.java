package com.matiasjuarez;

import com.matiasjuarez.config.server.ServerLauncher;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception {
        ServerLauncher serverLauncher = new ServerLauncher();
        serverLauncher.launch();
    }
}
