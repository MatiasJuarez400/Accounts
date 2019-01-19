//package com.matiasjuarez.monetaryaccount.transaction;
//
//import com.matiasjuarez.monetaryaccount.MonetaryAccount;
//
//public class TransactionErrorBuilder {
//    private TransactionErrorMessageModels transactionErrorMessageModels;
//
//    public TransactionErrorBuilder(TransactionErrorMessageModels transactionErrorMessageModels) {
//            this.transactionErrorMessageModels = transactionErrorMessageModels;
//    }
//
//    public TransactionError notOperativeMonetaryAccount(MonetaryAccount monetaryAccount) {
//        TransactionError transactionError = new TransactionError("001",
//                String.format(transactionErrorMessageModels.getNotActiveMonetaryAccountError(),
//                        monetaryAccount.getId(), monetaryAccount.getAccountStatus()));
//
//        return transactionError;
//    }
//}
