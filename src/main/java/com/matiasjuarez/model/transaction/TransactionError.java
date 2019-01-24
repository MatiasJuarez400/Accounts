package com.matiasjuarez.model.transaction;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.utils.DateFormatter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a problem when a transaction could not be executed successfully.
 * The data stored reflects the status of the entities at the moment when the error took place
 */
@DatabaseTable(tableName = "transactions_errors")
public class TransactionError {
    @DatabaseField(generatedId = true)
    private Long id;
    
    @DatabaseField
    private String originMonetaryAccountId;
    @DatabaseField
    private String originFunds;
    @DatabaseField
    private String originCurrency;
    @DatabaseField
    private String originStatus;
    @DatabaseField
    private String originCustomerAccountId;
    @DatabaseField
    private String originCustomerAccountBaseCountry;

    @DatabaseField
    private String targetMonetaryAccountId;
    @DatabaseField
    private String targetFunds;
    @DatabaseField
    private String targetCurrency;
    @DatabaseField
    private String targetStatus;
    @DatabaseField
    private String targetCustomerAccountId;
    @DatabaseField
    private String targetCustomerAccountBaseCountry;
    
    @DatabaseField
    private BigDecimal amountToTransfer;
    @DatabaseField
    private Date executionDate;
    @DatabaseField
    private String formattedDate;
    @DatabaseField
    private String error;

    public TransactionError(MonetaryAccount origin, MonetaryAccount target, BigDecimal amountToTransfer, Date executionDate, String error) {
        this.originMonetaryAccountId = origin.getId().toString();
        this.originFunds = origin.getFunds().toString();
        this.originCurrency = origin.getAccountCurrency().getTicker();
        this.originStatus = origin.getAccountStatus().toString();
        this.originCustomerAccountId = origin.getCustomerAccount().getId().toString();
        this.originCustomerAccountBaseCountry = origin.getCustomerAccount().getBaseCountry().getCode();

        this.targetMonetaryAccountId = target.getId().toString();
        this.targetFunds = target.getFunds().toString();
        this.targetCurrency = target.getAccountCurrency().getTicker();
        this.targetStatus = target.getAccountStatus().toString();
        this.targetCustomerAccountId = target.getCustomerAccount().getId().toString();
        this.targetCustomerAccountBaseCountry = target.getCustomerAccount().getBaseCountry().getCode();

        this.amountToTransfer = amountToTransfer;
        this.executionDate = executionDate;
        this.formattedDate = DateFormatter.format(executionDate);
        this.error = error;
    }

    public TransactionError() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginMonetaryAccountId() {
        return originMonetaryAccountId;
    }

    public String getOriginFunds() {
        return originFunds;
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

    public String getOriginCustomerAccountBaseCountry() {
        return originCustomerAccountBaseCountry;
    }

    public String getTargetMonetaryAccountId() {
        return targetMonetaryAccountId;
    }

    public String getTargetFunds() {
        return targetFunds;
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

    public String getTargetCustomerAccountBaseCountry() {
        return targetCustomerAccountBaseCountry;
    }

    public BigDecimal getAmountToTransfer() {
        return amountToTransfer;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public String getError() {
        return error;
    }
}
