package com.matiasjuarez;

import com.matiasjuarez.data.H2ServerLauncher;
import com.matiasjuarez.config.server.ServerLauncher;
import com.matiasjuarez.data.FakeDataLoader;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception {
        FakeDataLoader fakeDataLoader = new FakeDataLoader();
        fakeDataLoader.loadData();

        H2ServerLauncher h2ServerLauncher = new H2ServerLauncher();
        h2ServerLauncher.launch();

        ServerLauncher serverLauncher = new ServerLauncher();
        serverLauncher.launch();
    }
}
