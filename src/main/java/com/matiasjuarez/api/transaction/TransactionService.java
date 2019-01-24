package com.matiasjuarez.api.transaction;

import com.matiasjuarez.model.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(TransactionDTO transactionDTO) throws Exception;
    List<Transaction> getTransactionsForMonetaryAccount(Long monetaryAccountId) throws SQLException;
    List<Transaction> getTransactionsForCustomerAccount(Long customerAccountId) throws SQLException;
}
