package com.matiasjuarez.monetaryaccount.transaction;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.money.Currency;
import com.matiasjuarez.money.Money;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "transactions")
public class Transaction {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(foreign = true)
    private MonetaryAccount originAccount;
    @DatabaseField(foreign = true)
    private MonetaryAccount targetAccount;
    @DatabaseField
    private BigDecimal initialAmount;
    @DatabaseField(foreign = true, foreignAutoCreate = true)
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
    @DatabaseField
    private String errorCode;
    @DatabaseField
    private String errorReason;

    private Transaction() {}

    public Transaction(MonetaryAccount origin, MonetaryAccount target,
                       Money initialTransactionAmount, Money feesAmount, Money effectiveTransactionAmount) {
        this(origin, target, initialTransactionAmount, feesAmount, effectiveTransactionAmount, null, null);
    }

    public Transaction(MonetaryAccount origin, MonetaryAccount target,
                       Money initialTransactionAmount, Money feesAmount, Money effectiveTransactionAmount,
                       String errorCode, String errorReason) {
        this.originAccount = origin;
        this.targetAccount = target;

        this.initialAmount = initialTransactionAmount.getAmount();
        this.initialCurrency = initialTransactionAmount.getCurrency();

        this.feesAmount = feesAmount.getAmount();
        this.feesCurrency = feesAmount.getCurrency();

        this.effectiveAmount = effectiveTransactionAmount.getAmount();
        this.effectiveCurrency = effectiveTransactionAmount.getCurrency();

        this.errorCode = errorCode;
        this.errorReason = errorReason;

        this.executionDate = new Date();
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

    public Money getTransferedMoney() {
        return new Money(initialAmount, initialCurrency);
    }

    public Money getFees() {
        return new Money(feesAmount, feesCurrency);
    }

    public Money getEffectivelyTransferedMoney() {
        return new Money(effectiveAmount, effectiveCurrency);
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorReason() {
        return errorReason;
    }
}
