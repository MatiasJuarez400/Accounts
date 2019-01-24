package com.matiasjuarez.api.transaction;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.BeanFactory;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.customeraccount.CustomerAccountService;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.api.errorhandling.exceptions.InvalidTransactionException;
import com.matiasjuarez.api.monetaryaccount.MonetaryAccountService;
import com.matiasjuarez.api.transactionerror.TransactionErrorService;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.transaction.Transaction;
import com.matiasjuarez.model.transaction.TransactionError;
import com.matiasjuarez.model.transaction.TransactionHandler;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionServiceImpl extends BaseService implements TransactionService {
    private MonetaryAccountService monetaryAccountService;
    private CustomerAccountService customerAccountService;
    private TransactionErrorService transactionErrorService;
    private TransactionHandler transactionHandler;

    @Inject
    public TransactionServiceImpl(MonetaryAccountService monetaryAccountService,
                                  CustomerAccountService customerAccountService,
                                  TransactionErrorService transactionErrorService) {
        this.monetaryAccountService = monetaryAccountService;
        this.customerAccountService = customerAccountService;
        this.transactionErrorService = transactionErrorService;
        this.transactionHandler = BeanFactory.getInstance().getTransactionHandler();
    }

    public TransactionServiceImpl() {}


    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        MonetaryAccount origin = monetaryAccountService.getMonetaryAccount(transactionDTO.getOriginMonetaryAccountId());
        MonetaryAccount target = monetaryAccountService.getMonetaryAccount(transactionDTO.getTargetMonetaryAccountId());

        if (!transactionHandler.canExecuteTransaction(origin, target, transactionDTO.getAmount())) {
            TransactionError transactionError =
                    transactionHandler.getTransactionError(origin, target, transactionDTO.getAmount());

            transactionErrorService.createTransactionError(transactionError);

            throw new InvalidTransactionException(transactionError.getError());
        }

        Transaction transactionToSave = transactionHandler.buildTransaction(origin, target, transactionDTO.getAmount());

        monetaryAccountService.processTransaction(transactionToSave);

        return getDao().createIfNotExists(transactionToSave);
    }

    @Override
    public List<Transaction> getTransactionsForMonetaryAccount(Long monetaryAccountId) throws SQLException {
        if (monetaryAccountService.getMonetaryAccount(monetaryAccountId) == null) {
            throw new EntityNotFoundException(EntityNames.MONETARY_ACCOUNT, monetaryAccountId);
        }

        return queryTransactionsWithParams("originMonetaryAccountId",
                "targetMonetaryAccountId", monetaryAccountId);
    }

    @Override
    public List<Transaction> getTransactionsForCustomerAccount(Long customerAccountId) throws SQLException {
        if (customerAccountService.getCustomerAccount(customerAccountId) == null) {
            throw new EntityNotFoundException(EntityNames.CUSTOMER_ACCOUNT, customerAccountId);
        }

        return queryTransactionsWithParams("originCustomerAccountId",
                "targetCustomerAccountId", customerAccountId);
    }

    private List<Transaction> queryTransactionsWithParams(String originParam, String targetParam, Long accountId) throws SQLException {
        Map<String, Object> queryParamsOrigin = new HashMap<>();
        queryParamsOrigin.put(originParam, accountId);

        Map<String, Object> queryParamsTarget = new HashMap<>();
        queryParamsTarget.put(targetParam, accountId);

        List<Transaction> transactionsAsOrigin = getDao().queryForFieldValues(queryParamsOrigin);
        List<Transaction> transactionsAsTarget = getDao().queryForFieldValues(queryParamsTarget);

        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(transactionsAsOrigin);
        allTransactions.addAll(transactionsAsTarget);

        Map<Long, Transaction> filteredMap = new HashMap<>();
        for (Transaction transaction : allTransactions) {
            filteredMap.put(transaction.getId(), transaction);
        }

        return new ArrayList<>(filteredMap.values());
    }

    private Dao<Transaction, Long> getDao() {
        return inMemoryDBManager.getDaoForClass(Transaction.class);
    }
}
