package com.matiasjuarez.config.injection;

import com.matiasjuarez.money.CurrencyResource;
import org.glassfish.jersey.server.ResourceConfig;

public class AppResourceConfig extends ResourceConfig {

    public AppResourceConfig() {
        register(new AppBinder());
        register(CurrencyResource.class);
    }
}
