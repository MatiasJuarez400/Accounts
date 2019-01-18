package com.matiasjuarez.transaction;

import lombok.Data;

@Data
public class TransactionError {
    private String errorCode;
    private String reason;
}
