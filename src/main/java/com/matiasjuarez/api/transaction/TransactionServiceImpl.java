package com.matiasjuarez.api.transaction;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.BeanFactory;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.api.errorhandling.exceptions.InvalidTransactionException;
import com.matiasjuarez.api.monetaryaccount.MonetaryAccountService;
import com.matiasjuarez.api.transactionerror.TransactionErrorService;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.monetaryaccount.transaction.Transaction;
import com.matiasjuarez.model.monetaryaccount.transaction.TransactionError;
import com.matiasjuarez.model.monetaryaccount.transaction.TransactionHandler;
import com.matiasjuarez.utils.JsonConverter;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionServiceImpl extends BaseService implements TransactionService {
    private MonetaryAccountService monetaryAccountService;
    private TransactionErrorService transactionErrorService;
    private TransactionHandler transactionHandler;

    @Inject
    public TransactionServiceImpl(MonetaryAccountService monetaryAccountService,
                                  TransactionErrorService transactionErrorService) {
        this.monetaryAccountService = monetaryAccountService;
        this.transactionErrorService = transactionErrorService;
        this.transactionHandler = BeanFactory.getInstance().getTransactionHandler();
    }

    public TransactionServiceImpl() {}


    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) throws SQLException {
        MonetaryAccount origin = monetaryAccountService.getMonetaryAccount(transactionDTO.getOriginMonetaryAccountId());
        MonetaryAccount target = monetaryAccountService.getMonetaryAccount(transactionDTO.getTargetMonetaryAccountId());

        if (!transactionHandler.canExecuteTransaction(origin, target, transactionDTO.getAmount())) {
            TransactionError transactionError =
                    transactionHandler.getTransactionError(origin, target, transactionDTO.getAmount());

            transactionErrorService.createTransactionError(transactionError);

            throw new InvalidTransactionException(transactionError.getError());
        }

        Transaction transactionToSave = transactionHandler.buildTransaction(origin, target, transactionDTO.getAmount());

        return getDao().createIfNotExists(transactionToSave);
    }

    @Override
    public List<Transaction> getTransactions(Long monetaryAccountId) throws SQLException {
        if (monetaryAccountService.getMonetaryAccount(monetaryAccountId) == null) {
            throw new EntityNotFoundException(EntityNames.MONETARY_ACCOUNT, monetaryAccountId);
        }

        Map<String, Object> queryParamsOrigin = new HashMap<>();
        queryParamsOrigin.put("originaccount_id", monetaryAccountId);

        Map<String, Object> queryParamsTarget = new HashMap<>();
        queryParamsTarget.put("targetaccount_id", monetaryAccountId);

        List<Transaction> transactionsAsOrigin = getDao().queryForFieldValues(queryParamsOrigin);
        List<Transaction> transactionsAsTarget = getDao().queryForFieldValues(queryParamsTarget);

        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(transactionsAsOrigin);
        allTransactions.addAll(transactionsAsTarget);

        return allTransactions;
    }

    private Dao<Transaction, Long> getDao() {
        return inMemoryDBManager.getDaoForClass(Transaction.class);
    }
}
