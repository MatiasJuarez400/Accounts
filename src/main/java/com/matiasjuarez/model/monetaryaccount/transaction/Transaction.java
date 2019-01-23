package com.matiasjuarez.model.monetaryaccount.transaction;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.money.Currency;
import com.matiasjuarez.model.money.Money;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "transactions")
public class Transaction {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(foreign = true)
    private MonetaryAccount originAccount;
    @DatabaseField(foreign = true)
    private MonetaryAccount targetAccount;
    @DatabaseField
    private BigDecimal initialAmount;
    @DatabaseField(foreign = true)
    private Currency initialCurrency;
    @DatabaseField
    private BigDecimal feesAmount;
    @DatabaseField(foreign = true)
    private Currency feesCurrency;
    @DatabaseField
    private BigDecimal effectiveAmount;
    @DatabaseField(foreign = true)
    private Currency effectiveCurrency;
    @DatabaseField
    private Date executionDate;

    public Transaction() {}

    public Transaction(MonetaryAccount origin, MonetaryAccount target,
                       Money initialTransactionAmount, Money feesAmount, Money effectiveTransactionAmount,
                       Date executionDate) {
        this.originAccount = origin;
        this.targetAccount = target;

        this.initialAmount = initialTransactionAmount.getAmount();
        this.initialCurrency = initialTransactionAmount.getCurrency();

        this.feesAmount = feesAmount.getAmount();
        this.feesCurrency = feesAmount.getCurrency();

        this.effectiveAmount = effectiveTransactionAmount.getAmount();
        this.effectiveCurrency = effectiveTransactionAmount.getCurrency();

        this.executionDate = executionDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MonetaryAccount getOriginAccount() {
        return originAccount;
    }

    public MonetaryAccount getTargetAccount() {
        return targetAccount;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public Currency getInitialCurrency() {
        return initialCurrency;
    }

    public BigDecimal getFeesAmount() {
        return feesAmount;
    }

    public Currency getFeesCurrency() {
        return feesCurrency;
    }

    public BigDecimal getEffectiveAmount() {
        return effectiveAmount;
    }

    public Currency getEffectiveCurrency() {
        return effectiveCurrency;
    }

    public Date getExecutionDate() {
        return executionDate;
    }
}
