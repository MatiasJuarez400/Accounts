package com.matiasjuarez.model.monetaryaccount.transaction;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a problem when a transaction could not be executed successfully
 */
@DatabaseTable(tableName = "transactions_errors")
public class TransactionError {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(foreign = true)
    private MonetaryAccount origin;
    @DatabaseField(foreign = true)
    private MonetaryAccount target;
    @DatabaseField
    private BigDecimal amountToTransfer;
    @DatabaseField
    private Date executionDate;
    @DatabaseField
    private String error;

    public TransactionError(MonetaryAccount origin, MonetaryAccount target, BigDecimal amountToTransfer, Date executionDate, String error) {
        this.origin = origin;
        this.target = target;
        this.amountToTransfer = amountToTransfer;
        this.executionDate = executionDate;
        this.error = error;
    }

    public TransactionError() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonetaryAccount getOrigin() {
        return origin;
    }

    public MonetaryAccount getTarget() {
        return target;
    }

    public BigDecimal getAmountToTransfer() {
        return amountToTransfer;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public String getError() {
        return error;
    }
}
