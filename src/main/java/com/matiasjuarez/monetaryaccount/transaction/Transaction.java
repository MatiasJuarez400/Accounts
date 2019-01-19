package com.matiasjuarez.monetaryaccount.transaction;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.money.Money;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "transactions")
public class Transaction {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private long originAccount;
    @DatabaseField
    private long targetAccount;
    @DatabaseField
    private BigDecimal initialAmount;
    @DatabaseField
    private String initialCurrency;
    @DatabaseField
    private BigDecimal feesAmount;
    @DatabaseField
    private String feesCurrency;
    @DatabaseField
    private BigDecimal effectiveAmount;
    @DatabaseField
    private String effectiveCurrency;

    @DatabaseField
    private Date executionDate;
    @DatabaseField(foreign = true)
    private TransactionError transactionError;
    @DatabaseField(foreign = true)
    private MonetaryAccount monetaryAccount;

    public Transaction() {
    }

    public Transaction(MonetaryAccount origin, MonetaryAccount target) {
        this.originAccount = origin.getId();
        this.targetAccount = target.getId();
        this.executionDate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOriginAccount() {
        return originAccount;
    }

    public long getTargetAccount() {
        return targetAccount;
    }

    public Money getTransferedMoney() {
        return new Money(initialAmount, initialCurrency);
    }

    public void setTransferedMoney(Money transferedMoney) {
        this.initialAmount = transferedMoney.getAmount();
        this.initialCurrency = transferedMoney.getCurrencyCode();
    }

    public Money getFees() {
        return new Money(feesAmount, feesCurrency);
    }

    public void setFees(Money fees) {
        this.feesAmount = fees.getAmount();
        this.feesCurrency = fees.getCurrencyCode();
    }

    public Money getEffectivelyTransferedMoney() {
        return new Money(effectiveAmount, effectiveCurrency);
    }

    public void setEffectivelyTransferedMoney(Money effectivelyTransferedMoney) {
        this.effectiveAmount = effectivelyTransferedMoney.getAmount();
        this.effectiveCurrency = effectivelyTransferedMoney.getCurrencyCode();
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public TransactionError getTransactionError() {
        return transactionError;
    }

    public void setTransactionError(TransactionError transactionError) {
        this.transactionError = transactionError;
    }
}
