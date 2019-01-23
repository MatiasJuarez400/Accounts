package com.matiasjuarez.api.errorhandling.exceptions;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String msg) {
        super(msg);
    }
}
