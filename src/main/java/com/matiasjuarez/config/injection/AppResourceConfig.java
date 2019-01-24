package com.matiasjuarez.config.injection;

import com.matiasjuarez.api.country.CountryResource;
import com.matiasjuarez.api.currency.CurrencyResource;
import com.matiasjuarez.api.customer.CustomerResource;
import com.matiasjuarez.api.customeraccount.CustomerAccountResource;
import com.matiasjuarez.api.errorhandling.*;
import com.matiasjuarez.api.monetaryaccount.MonetaryAccountResource;
import com.matiasjuarez.api.transaction.TransactionResource;
import com.matiasjuarez.api.transactionerror.TransactionErrorResource;
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
        register(CountryResource.class);
        register(CustomerAccountResource.class);
        register(MonetaryAccountResource.class);
        register(TransactionResource.class);
        register(TransactionErrorResource.class);
    }

    private void registerExceptionMappers() {
        register(SQLExceptionMapper.class);
        register(RuntimeExceptionMapper.class);
        register(EntityNotFoundExceptionMapper.class);
        register(BadRequestExceptionMapper.class);
        register(UpdateNotPerformedExceptionMapper.class);
        register(InconsistentDataExceptionMapper.class);
        register(IllegalUpdateExceptionMapper.class);
        register(ExceptionMapper.class);
        register(InvalidTransactionExceptionMapper.class);
        register(CustomerAccountAlreadyHaveMonetaryAccountExceptionMapper.class);
    }
}
