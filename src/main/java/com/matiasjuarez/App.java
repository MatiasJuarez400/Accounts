package com.matiasjuarez;

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

        ServerLauncher serverLauncher = new ServerLauncher();
        serverLauncher.launch();
    }
}
