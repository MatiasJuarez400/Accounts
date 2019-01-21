package com.matiasjuarez.config.injection;

import com.matiasjuarez.errorhandling.RuntimeExceptionMapper;
import com.matiasjuarez.errorhandling.SQLExceptionMapper;
import com.matiasjuarez.money.CurrencyResource;
import org.glassfish.jersey.server.ResourceConfig;

public class AppResourceConfig extends ResourceConfig {

    public AppResourceConfig() {
        register(new AppBinder());
        register(CurrencyResource.class);
        registerExceptionMappers();
    }

    private void registerExceptionMappers() {
        register(SQLExceptionMapper.class);
        register(RuntimeExceptionMapper.class);
    }
}
