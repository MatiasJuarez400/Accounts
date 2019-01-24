package com.matiasjuarez.api.transactionerror;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.customeraccount.CustomerAccountService;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.api.monetaryaccount.MonetaryAccountService;
import com.matiasjuarez.model.transaction.TransactionError;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionErrorServiceImpl extends BaseService implements TransactionErrorService {
    @Inject
    private MonetaryAccountService monetaryAccountService;
    @Inject
    private CustomerAccountService customerAccountService;

    public TransactionErrorServiceImpl(MonetaryAccountService monetaryAccountService, CustomerAccountService customerAccountService) {
        this.monetaryAccountService = monetaryAccountService;
        this.customerAccountService = customerAccountService;
    }

    public TransactionErrorServiceImpl() {}

    @Override
    public List<TransactionError> getMonetaryAccountTransactionErrors(Long monetaryAccountId) throws SQLException {
        if (monetaryAccountService.getMonetaryAccount(monetaryAccountId) == null) {
            throw new EntityNotFoundException(EntityNames.MONETARY_ACCOUNT, monetaryAccountId);
        }

        return queryTransactionsWithParams("originMonetaryAccountId",
                "targetMonetaryAccountId", monetaryAccountId);
    }

    @Override
    public List<TransactionError> getCustomerAccountTransactionErrors(Long customerAccountId) throws SQLException {
        if (customerAccountService.getCustomerAccount(customerAccountId) == null) {
            throw new EntityNotFoundException(EntityNames.CUSTOMER_ACCOUNT, customerAccountId);
        }

        return queryTransactionsWithParams("originCustomerAccountId",
                "targetCustomerAccountId", customerAccountId);
    }

    private List<TransactionError> queryTransactionsWithParams(String originParam, String targetParam, Long accountId) throws SQLException {
        Map<String, Object> queryParamsOrigin = new HashMap<>();
        queryParamsOrigin.put(originParam, accountId);

        Map<String, Object> queryParamsTarget = new HashMap<>();
        queryParamsTarget.put(targetParam, accountId);

        List<TransactionError> transactionsAsOrigin = getDao().queryForFieldValues(queryParamsOrigin);
        List<TransactionError> transactionsAsTarget = getDao().queryForFieldValues(queryParamsTarget);

        List<TransactionError> allTransactions = new ArrayList<>();
        allTransactions.addAll(transactionsAsOrigin);
        allTransactions.addAll(transactionsAsTarget);

        Map<Long, TransactionError> filteredMap = new HashMap<>();
        for (TransactionError transaction : allTransactions) {
            filteredMap.put(transaction.getId(), transaction);
        }

        return new ArrayList<>(filteredMap.values());
    }

    @Override
    public TransactionError createTransactionError(TransactionError transactionError) throws SQLException {
        TransactionError created = getDao().createIfNotExists(transactionError);
        return created;
    }

    private Dao<TransactionError, Long> getDao() {
        return inMemoryDBManager.getDaoForClass(TransactionError.class);
    }
}
