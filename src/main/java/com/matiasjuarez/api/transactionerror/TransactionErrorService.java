package com.matiasjuarez.api.transactionerror;

import com.matiasjuarez.model.monetaryaccount.transaction.TransactionError;

import java.sql.SQLException;
import java.util.List;

public interface TransactionErrorService {
    List<TransactionError> getMonetaryAccountTransactionErrors(Long monetaryAccountId) throws SQLException;
    TransactionError createTransactionError(TransactionError transactionError) throws SQLException;
}
