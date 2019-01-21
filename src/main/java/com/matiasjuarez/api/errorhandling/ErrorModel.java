package com.matiasjuarez.api.errorhandling;

import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;
import com.matiasjuarez.utils.JsonConverter;

import javax.ws.rs.BadRequestException;
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

    public ErrorModel(EntityNotFoundException entityNotFoundException) {
        this.errorCode = 3;
        this.errorMessage = entityNotFoundException.getMessage();
    }

    public ErrorModel(BadRequestException badRequestException) {
        this.errorCode = 4;
        this.errorMessage = badRequestException.getMessage();
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
