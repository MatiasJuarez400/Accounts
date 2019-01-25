package com.matiasjuarez.api.monetaryaccount;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.currency.CurrencyService;
import com.matiasjuarez.api.customeraccount.CustomerAccountService;
import com.matiasjuarez.api.errorhandling.exceptions.CustomerAccountAlreadyHaveMonetaryAccountException;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.api.errorhandling.exceptions.IllegalUpdateException;
import com.matiasjuarez.api.errorhandling.exceptions.UpdateNotPerformedException;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.transaction.Transaction;
import com.matiasjuarez.model.money.Currency;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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

    /**
     * Creates a new monetary account for a customer account if the monetary account to create
     * has a valid currency, a customer account exists with the specified id and the customer account
     * does not have a monetary account with the same currency as the new one.
     * @param monetaryAccountToCreate
     * @return
     * @throws SQLException
     */
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

        for (MonetaryAccount monetaryAccount : storedCustomerAccount.getMonetaryAccounts()) {
            if (monetaryAccount.getAccountCurrency().getTicker().equalsIgnoreCase(accountTicker)) {
                throw new CustomerAccountAlreadyHaveMonetaryAccountException(customerAccountId, accountTicker);
            }
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

    @Override
    public void processTransaction(Transaction transaction) throws Exception {
        doProcess(transaction, getDao());
    }

    private static synchronized void doProcess(Transaction transaction, Dao<MonetaryAccount, Long> dao) throws Exception {
        MonetaryAccount origin = dao.queryForId(Long.valueOf(transaction.getOriginMonetaryAccountId()));
        MonetaryAccount target = dao.queryForId(Long.valueOf(transaction.getTargetMonetaryAccountId()));

        origin.setFunds(origin.getFunds().subtract(new BigDecimal(transaction.getTransferAmount())));
        target.setFunds(target.getFunds().add(new BigDecimal(transaction.getEffectiveAmount())));

        // Execute updates inside a single transaction
        dao.callBatchTasks(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                dao.update(origin);
                dao.update(target);

                return true;
            }
        });
    }

    private Dao<MonetaryAccount, Long> getDao() {
        return inMemoryDBManager.getDaoForClass(MonetaryAccount.class);
    }
}
