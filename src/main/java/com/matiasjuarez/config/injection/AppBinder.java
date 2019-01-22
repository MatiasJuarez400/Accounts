package com.matiasjuarez.config.injection;

import com.matiasjuarez.api.currency.CurrencyService;
import com.matiasjuarez.api.currency.CurrencyServiceImpl;
import com.matiasjuarez.api.customer.CustomerService;
import com.matiasjuarez.api.customer.CustomerServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AppBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(CurrencyServiceImpl.class).to(CurrencyService.class);
        bind(CustomerServiceImpl.class).to(CustomerService.class);
    }
}
