package com.matiasjuarez.api.transactionerror;

import com.j256.ormlite.dao.Dao;
import com.matiasjuarez.api.BaseService;
import com.matiasjuarez.api.EntityNames;
import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.api.monetaryaccount.MonetaryAccountService;
import com.matiasjuarez.model.monetaryaccount.transaction.TransactionError;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionErrorServiceImpl extends BaseService implements TransactionErrorService {
    @Inject
    private MonetaryAccountService monetaryAccountService;

    public TransactionErrorServiceImpl(MonetaryAccountService monetaryAccountService) {
        this.monetaryAccountService = monetaryAccountService;
    }

    public TransactionErrorServiceImpl() {}

    @Override
    public List<TransactionError> getMonetaryAccountTransactionErrors(Long monetaryAccountId) throws SQLException {
        if (monetaryAccountService.getMonetaryAccount(monetaryAccountId) == null) {
            throw new EntityNotFoundException(EntityNames.MONETARY_ACCOUNT, monetaryAccountId);
        }

        Map<String, Object> queryParamsOrigin = new HashMap<>();
        queryParamsOrigin.put("originid", monetaryAccountId);

        Map<String, Object> queryParamsTarget = new HashMap<>();
        queryParamsTarget.put("targetid", monetaryAccountId);

        List<TransactionError> originTransactionErrors = getDao().queryForFieldValues(queryParamsOrigin);
        List<TransactionError> targetTransactionErrors = getDao().queryForFieldValues(queryParamsTarget);
        List<TransactionError> allTransactionErrors = new ArrayList<>();
        allTransactionErrors.addAll(originTransactionErrors);
        allTransactionErrors.addAll(targetTransactionErrors);



        return allTransactionErrors;
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
