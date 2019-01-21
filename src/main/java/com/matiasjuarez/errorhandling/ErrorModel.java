package com.matiasjuarez.errorhandling;

import com.matiasjuarez.utils.JsonConverter;

import java.sql.SQLException;

public class ErrorModel {
    private int errorCode;
    private String errorMessage;

    public ErrorModel(SQLException sqlException) {
        this.errorCode = 1;
        this.errorMessage = sqlException.getMessage();
    }

    public ErrorModel(RuntimeException runtimeException) {
        this.errorCode = 2;
        this.errorMessage = runtimeException.getMessage();
    }

    public String toJson() {
        return JsonConverter.convert(this);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
