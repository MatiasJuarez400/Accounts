package com.matiasjuarez.api.transaction;

import com.matiasjuarez.model.monetaryaccount.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(TransactionDTO transactionDTO) throws SQLException;
    List<Transaction> getTransactions(Long monetaryAccountId) throws SQLException;
}
