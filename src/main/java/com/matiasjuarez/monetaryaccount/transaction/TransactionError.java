package com.matiasjuarez.monetaryaccount.transaction;

public class TransactionError {
    private String errorCode;
    private String reason;

    public TransactionError(String errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
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
