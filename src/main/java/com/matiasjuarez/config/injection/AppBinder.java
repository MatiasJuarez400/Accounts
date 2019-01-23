package com.matiasjuarez.config.injection;

import com.matiasjuarez.api.country.CountryService;
import com.matiasjuarez.api.country.CountryServiceImpl;
import com.matiasjuarez.api.currency.CurrencyService;
import com.matiasjuarez.api.currency.CurrencyServiceImpl;
import com.matiasjuarez.api.customer.CustomerService;
import com.matiasjuarez.api.customer.CustomerServiceImpl;
import com.matiasjuarez.api.customeraccount.CustomerAccountService;
import com.matiasjuarez.api.customeraccount.CustomerAccountServiceImpl;
import com.matiasjuarez.api.monetaryaccount.MonetaryAccountService;
import com.matiasjuarez.api.monetaryaccount.MonetaryAccountServiceImpl;
import com.matiasjuarez.api.transaction.TransactionService;
import com.matiasjuarez.api.transaction.TransactionServiceImpl;
import com.matiasjuarez.api.transactionerror.TransactionErrorService;
import com.matiasjuarez.api.transactionerror.TransactionErrorServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AppBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(CurrencyServiceImpl.class).to(CurrencyService.class);
        bind(CustomerServiceImpl.class).to(CustomerService.class);
        bind(CountryServiceImpl.class).to(CountryService.class);
        bind(CustomerAccountServiceImpl.class).to(CustomerAccountService.class);
        bind(MonetaryAccountServiceImpl.class).to(MonetaryAccountService.class);
        bind(TransactionServiceImpl.class).to(TransactionService.class);
        bind(TransactionErrorServiceImpl.class).to(TransactionErrorService.class);
    }
}
