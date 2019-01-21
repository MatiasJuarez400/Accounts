package com.matiasjuarez.config.injection;

import com.matiasjuarez.money.CurrencyService;
import com.matiasjuarez.money.CurrencyServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AppBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(CurrencyServiceImpl.class).to(CurrencyService.class);
    }
}
