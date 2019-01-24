package com.matiasjuarez.model.transaction;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.money.Money;
import com.matiasjuarez.utils.DateFormatter;

import java.util.Date;

@DatabaseTable(tableName = "transactions")
public class Transaction {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String originMonetaryAccountId;
    @DatabaseField
    private String originFundsBeforeTransaction;
    @DatabaseField
    private String originFundsAfterTransaction;
    @DatabaseField
    private String originCurrency;
    @DatabaseField
    private String originStatus;
    @DatabaseField
    private String originCustomerAccountId;
    @DatabaseField
    private String originCustomerAccountCountry;

    @DatabaseField
    private String targetMonetaryAccountId;
    @DatabaseField
    private String targetFundsBeforeTransaction;
    @DatabaseField
    private String targetFundsAfterTransaction;
    @DatabaseField
    private String targetCurrency;
    @DatabaseField
    private String targetStatus;
    @DatabaseField
    private String targetCustomerAccountId;
    @DatabaseField
    private String targetCustomerAccountCountry;

    @DatabaseField
    private String transferAmount;
    @DatabaseField
    private String transferAmountCurrency;

    @DatabaseField
    private String feeAmount;
    @DatabaseField
    private String feeAmountCurrency;

    @DatabaseField
    private String effectiveAmount;
    @DatabaseField
    private String effectiveAmountCurrency;

    @DatabaseField
    private Date executionDate;
    @DatabaseField
    private String formatedDate;

    @DatabaseField(foreign = true)
    private MonetaryAccount monetaryAccount;

    public Transaction() {}

    public Transaction(MonetaryAccount origin, MonetaryAccount target,
                       Money transferAmount, Money feeAmount, Money effectiveAmount,
                       Date executionDate) {
        this.originMonetaryAccountId = origin.getId().toString();
        this.originFundsBeforeTransaction = origin.getFunds().toString();
        this.originFundsAfterTransaction = origin.getFunds().subtract(transferAmount.getAmount()).toString();
        this.originCurrency = origin.getAccountCurrency().getTicker();
        this.originStatus = origin.getAccountStatus().toString();
        this.originCustomerAccountId = origin.getCustomerAccount().getId().toString();
        this.originCustomerAccountCountry = origin.getCustomerAccount().getBaseCountry().getCode();

        this.targetMonetaryAccountId = target.getId().toString();
        this.targetFundsBeforeTransaction = target.getFunds().toString();
        this.targetFundsAfterTransaction = target.getFunds().add(effectiveAmount.getAmount()).toString();
        this.targetCurrency = target.getAccountCurrency().getTicker();
        this.targetStatus = target.getAccountStatus().toString();
        this.targetCustomerAccountId = target.getCustomerAccount().getId().toString();
        this.targetCustomerAccountCountry = target.getCustomerAccount().getBaseCountry().getCode();

        this.transferAmount = transferAmount.getAmount().toString();
        this.transferAmountCurrency = transferAmount.getCurrency().getTicker();

        this.feeAmount = feeAmount.getAmount().toString();
        this.feeAmountCurrency = feeAmount.getCurrency().getTicker();

        this.effectiveAmount = effectiveAmount.getAmount().toString();
        this.effectiveAmountCurrency = effectiveAmount.getCurrency().getTicker();

        this.executionDate = executionDate;
        this.formatedDate = DateFormatter.format(executionDate);

        this.monetaryAccount = origin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginMonetaryAccountId() {
        return originMonetaryAccountId;
    }

    public String getOriginFundsBeforeTransaction() {
        return originFundsBeforeTransaction;
    }

    public String getOriginFundsAfterTransaction() {
        return originFundsAfterTransaction;
    }

    public String getOriginCurrency() {
        return originCurrency;
    }

    public String getOriginStatus() {
        return originStatus;
    }

    public String getOriginCustomerAccountId() {
        return originCustomerAccountId;
    }

    public String getOriginCustomerAccountCountry() {
        return originCustomerAccountCountry;
    }

    public String getTargetMonetaryAccountId() {
        return targetMonetaryAccountId;
    }

    public String getTargetFundsBeforeTransaction() {
        return targetFundsBeforeTransaction;
    }

    public String getTargetFundsAfterTransaction() {
        return targetFundsAfterTransaction;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public String getTargetStatus() {
        return targetStatus;
    }

    public String getTargetCustomerAccountId() {
        return targetCustomerAccountId;
    }

    public String getTargetCustomerAccountCountry() {
        return targetCustomerAccountCountry;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public String getTransferAmountCurrency() {
        return transferAmountCurrency;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public String getFeeAmountCurrency() {
        return feeAmountCurrency;
    }

    public String getEffectiveAmount() {
        return effectiveAmount;
    }

    public String getEffectiveAmountCurrency() {
        return effectiveAmountCurrency;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public String getFormatedDate() {
        return formatedDate;
    }
}
