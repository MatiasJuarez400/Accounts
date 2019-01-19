package com.matiasjuarez.monetaryaccount.transaction;

import com.j256.ormlite.field.DatabaseField;

public class TransactionError {
    @DatabaseField(id = true)
    private String errorCode;
    @DatabaseField
    private String reason;

    public TransactionError(String errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

    public TransactionError() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
