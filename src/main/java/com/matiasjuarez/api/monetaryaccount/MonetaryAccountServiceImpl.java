package com.matiasjuarez.api.monetaryaccount;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.customeraccount.CustomerAccountService;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.api.errorhandling.exceptions.UpdateNotPerformedException;
import com.matiasjuarez.monetaryaccount.MonetaryAccount;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonetaryAccountServiceImpl extends BaseService implements MonetaryAccountService {
    @Inject
    private CustomerAccountService customerAccountService;

    @Override
    public MonetaryAccount createMonetaryAccount(MonetaryAccount monetaryAccount) throws SQLException {
        return getDao().createIfNotExists(monetaryAccount);
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
        return getDao().queryForId(monetaryAccountId);
    }

    private Dao<MonetaryAccount, Long> getDao() {
        return inMemoryDBManager.getDaoForClass(MonetaryAccount.class);
    }
}
