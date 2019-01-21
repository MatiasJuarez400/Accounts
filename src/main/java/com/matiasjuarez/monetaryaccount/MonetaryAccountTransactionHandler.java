//package com.matiasjuarez.monetaryaccount;
//
//import com.matiasjuarez.monetaryaccount.transaction.TransactionErrorBuilder;
//import com.matiasjuarez.money.Money;
//import com.matiasjuarez.monetaryaccount.transaction.Transaction;
//
//public class MonetaryAccountTransactionHandler {
//    private TransactionErrorBuilder transactionErrorBuilder;
//
//    public MonetaryAccountTransactionHandler(TransactionErrorBuilder transactionErrorBuilder) {
//        this.transactionErrorBuilder = transactionErrorBuilder;
//    }
//
//    public Transaction executeTransference(MonetaryAccount origin, MonetaryAccount target, Money moneyToTransfer) {
//        Transaction transaction = new Transaction(origin, target);
//
//        if (!origin.isOperative()) {
//            transaction.setTransactionError(transactionErrorBuilder.notOperativeMonetaryAccount(origin));
//        } else if (!target.isOperative()) {
//            transaction.setTransactionError(transactionErrorBuilder.notOperativeMonetaryAccount(target));
//        }
//
//        return transaction;
//    }
//}
