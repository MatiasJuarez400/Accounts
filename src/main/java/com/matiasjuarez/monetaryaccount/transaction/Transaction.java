package com.matiasjuarez.monetaryaccount.transaction;

import com.matiasjuarez.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.money.Money;


import java.util.Date;

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

    public String getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(String originAccount) {
        this.originAccount = originAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public Money getTransferedMoney() {
        return transferedMoney;
    }

    public void setTransferedMoney(Money transferedMoney) {
        this.transferedMoney = transferedMoney;
    }

    public Money getFees() {
        return fees;
    }

    public void setFees(Money fees) {
        this.fees = fees;
    }

    public Money getEffectivelyTransferedMoney() {
        return effectivelyTransferedMoney;
    }

    public void setEffectivelyTransferedMoney(Money effectivelyTransferedMoney) {
        this.effectivelyTransferedMoney = effectivelyTransferedMoney;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public TransactionError getTransactionError() {
        return transactionError;
    }

    public void setTransactionError(TransactionError transactionError) {
        this.transactionError = transactionError;
    }
}
