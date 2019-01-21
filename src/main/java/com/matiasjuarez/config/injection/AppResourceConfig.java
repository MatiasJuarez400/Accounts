package com.matiasjuarez.config.injection;

import com.matiasjuarez.api.customer.CustomerResource;
import com.matiasjuarez.api.errorhandling.BadRequestExceptionMapper;
import com.matiasjuarez.api.errorhandling.EntityNotFoundExceptionMapper;
import com.matiasjuarez.api.errorhandling.RuntimeExceptionMapper;
import com.matiasjuarez.api.errorhandling.SQLExceptionMapper;
import com.matiasjuarez.api.currency.CurrencyResource;
import org.glassfish.jersey.server.ResourceConfig;

public class AppResourceConfig extends ResourceConfig {

    public AppResourceConfig() {
        register(new AppBinder());
        registerResources();
        registerExceptionMappers();
    }

    private void registerResources() {
        register(CurrencyResource.class);
        register(CustomerResource.class);
    }

    private void registerExceptionMappers() {
        register(SQLExceptionMapper.class);
        register(RuntimeExceptionMapper.class);
        register(EntityNotFoundExceptionMapper.class);
        register(BadRequestExceptionMapper.class);
    }
}
