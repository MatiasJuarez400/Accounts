package com.matiasjuarez.model.transaction;

import lombok.Data;

@Data
public class TransactionError {
    private String errorCode;
    private String reason;
}
