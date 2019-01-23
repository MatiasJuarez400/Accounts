package com.matiasjuarez.api.errorhandling.exceptions;

public class InconsistentDataException extends RuntimeException {
    public InconsistentDataException(String msg) {
        super(msg);
    }
}
