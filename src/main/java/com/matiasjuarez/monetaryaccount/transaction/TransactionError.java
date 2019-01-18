package com.matiasjuarez.monetaryaccount.transaction;

import com.matiasjuarez.monetaryaccount.MonetaryAccount;
import lombok.Data;

@Data
public class TransactionError {
    private String errorCode;
    private String reason;

    public TransactionError(String errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }


}
