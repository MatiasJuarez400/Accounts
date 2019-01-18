package com.matiasjuarez.monetaryaccount.transaction;

import com.matiasjuarez.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.money.Money;
import lombok.Data;

import java.util.Date;

@Data
public class Transaction {
    private String originAccount;
    private String targetAccount;
    private Money transferedMoney;
    private Money fees;
    private Money effectivelyTransferedMoney;
    private Date executionDate;
    private TransactionError transactionError;

    public Transaction(MonetaryAccount origin, MonetaryAccount target) {
        this.originAccount = origin.getId();
        this.targetAccount = target.getId();
        this.executionDate = new Date();
    }
}
