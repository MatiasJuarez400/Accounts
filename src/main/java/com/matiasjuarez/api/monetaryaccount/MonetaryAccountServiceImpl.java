package com.matiasjuarez.api.monetaryaccount;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.currency.CurrencyService;
import com.matiasjuarez.api.customeraccount.CustomerAccountService;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.api.errorhandling.exceptions.IllegalUpdateException;
import com.matiasjuarez.api.errorhandling.exceptions.UpdateNotPerformedException;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.money.Currency;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonetaryAccountServiceImpl extends BaseService implements MonetaryAccountService {
    @Inject
    private CustomerAccountService customerAccountService;
    @Inject
    private CurrencyService currencyService;

    public MonetaryAccountServiceImpl() {}

    public MonetaryAccountServiceImpl(CustomerAccountService customerAccountService, CurrencyService currencyService) {
        this.customerAccountService = customerAccountService;
        this.currencyService = currencyService;
    }

    @Override
    public MonetaryAccount createMonetaryAccount(MonetaryAccount monetaryAccountToCreate) throws SQLException {
        String accountTicker = monetaryAccountToCreate.getAccountCurrency().getTicker();

        Currency storedCurrency = currencyService.getCurrency(accountTicker);
        if (storedCurrency == null) {
            throw new EntityNotFoundException(EntityNames.CURRENCY, accountTicker);
        }

        Long customerAccountId = monetaryAccountToCreate.getCustomerAccount().getId();
        CustomerAccount storedCustomerAccount = customerAccountService.getCustomerAccount(customerAccountId);
        if (storedCustomerAccount == null) {
            throw new EntityNotFoundException(EntityNames.CUSTOMER_ACCOUNT, customerAccountId);
        }

        monetaryAccountToCreate.setAccountCurrency(storedCurrency);
        monetaryAccountToCreate.setCustomerAccount(storedCustomerAccount);

        MonetaryAccount createdMonetaryAccount = getDao().createIfNotExists(monetaryAccountToCreate);

        return getMonetaryAccount(createdMonetaryAccount.getId());
    }

    @Override
    public List<MonetaryAccount> getMonetaryAccountsFromCustomerAccount(Long customerAccountId) throws SQLException {
        if (customerAccountService.getCustomerAccount(customerAccountId) == null) {
            throw new EntityNotFoundException(EntityNames.CUSTOMER_ACCOUNT, customerAccountId);
        }

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("customeraccount_id", customerAccountId);

        List<MonetaryAccount> monetaryAccounts = getDao().queryForFieldValues(queryParams);

        return monetaryAccounts;
    }

    /**
     * This will only enable an update on the accountStatus field. In order to update the account funds,
     * you have to perform a transaction.
     * @param monetaryAccountUpdateData - the object that holds the new data
     * @return a MonetaryAccount object with the updated data
     */
    @Override
    public MonetaryAccount updateMonetaryAccount(MonetaryAccount monetaryAccountUpdateData) throws SQLException {
        Long customerAccountId = monetaryAccountUpdateData.getCustomerAccount().getId();
        Long monetaryAccountId = monetaryAccountUpdateData.getId();

        List<MonetaryAccount> storedMonetaryAccounts = getMonetaryAccountsFromCustomerAccount(customerAccountId);

        if (storedMonetaryAccounts == null || storedMonetaryAccounts.isEmpty()) {
            throw new EntityNotFoundException(EntityNames.MONETARY_ACCOUNT, monetaryAccountId);
        } else {
            // This code validates that the monetary account we are trying to update belongs to the customerAccount with id 'customerAccountId'
            boolean foundAccount = false;
            for (MonetaryAccount storedAccount : storedMonetaryAccounts) {
                if (storedAccount.getId() == monetaryAccountId) {
                    foundAccount = true;
                    break;
                }
            }

            if (!foundAccount) {
                throw new IllegalUpdateException(EntityNames.CUSTOMER_ACCOUNT, customerAccountId, EntityNames.MONETARY_ACCOUNT, monetaryAccountId);
            }
        }

        MonetaryAccount storedAccount = getDao().queryForId(monetaryAccountUpdateData.getId());

        if (storedAccount.getAccountStatus() != monetaryAccountUpdateData.getAccountStatus()) {
            storedAccount.setAccountStatus(monetaryAccountUpdateData.getAccountStatus());
            int updateResult = getDao().update(storedAccount);

            if (updateResult != 1) {
                throw new UpdateNotPerformedException(EntityNames.MONETARY_ACCOUNT, storedAccount.getId());
            }
        }

        return storedAccount;
    }

    @Override
    public MonetaryAccount getMonetaryAccount(Long monetaryAccountId) throws SQLException {
        MonetaryAccount retrievedMonetaryAccount = getDao().queryForId(monetaryAccountId);
        retrievedMonetaryAccount.setCustomerAccount(customerAccountService.getCustomerAccount(retrievedMonetaryAccount.getCustomerAccount().getId()));

        return retrievedMonetaryAccount;
    }

    private Dao<MonetaryAccount, Long> getDao() {
        return inMemoryDBManager.getDaoForClass(MonetaryAccount.class);
    }
}
