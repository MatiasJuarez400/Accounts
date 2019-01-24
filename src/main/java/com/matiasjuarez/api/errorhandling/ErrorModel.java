package com.matiasjuarez.api.errorhandling;

import com.matiasjuarez.api.errorhandling.exceptions.*;
import com.matiasjuarez.utils.JsonConverter;

import javax.ws.rs.BadRequestException;
import java.sql.SQLException;

public class ErrorModel {
    private int errorCode;
    private String errorDescription;
    private String errorMessage;

    public ErrorModel(SQLException sqlException) {
        this.errorCode = 1;
        this.errorDescription = "Database problem";
        this.errorMessage = sqlException.getMessage();
    }

    public ErrorModel(RuntimeException runtimeException) {
        this.errorCode = 2;
        this.errorDescription = "General problem";
        this.errorMessage = runtimeException.getMessage();
    }

    public ErrorModel(EntityNotFoundException entityNotFoundException) {
        this.errorCode = 3;
        this.errorDescription = "Entity not found";
        this.errorMessage = entityNotFoundException.getMessage();
    }

    public ErrorModel(BadRequestException badRequestException) {
        this.errorCode = 4;
        this.errorDescription = "Bad Request";
        this.errorMessage = badRequestException.getMessage();
    }

    public ErrorModel(UpdateNotPerformedException updateNotPerformedException) {
        this.errorCode = 5;
        this.errorDescription = "Update not performed";
        this.errorMessage = updateNotPerformedException.getMessage();
    }

    public ErrorModel(InconsistentDataException inconsistentDataException) {
        this.errorCode = 6;
        this.errorDescription = "Inconsistent data";
        this.errorMessage = inconsistentDataException.getMessage();
    }

    public ErrorModel(IllegalUpdateException illegalUpdateException) {
        this.errorCode = 7;
        this.errorDescription = "Illegal update";
        this.errorMessage = illegalUpdateException.getMessage();
    }

    public ErrorModel(Exception exception) {
        this.errorCode = 8;
        this.errorDescription = "Unknown exception";
        this.errorMessage = exception.getMessage();
    }

    public ErrorModel(InvalidTransactionException invalidTransactionException) {
        this.errorCode = 9;
        this.errorDescription = "Invalidad transaction";
        this.errorMessage = invalidTransactionException.getMessage();
    }

    public ErrorModel(CustomerAccountAlreadyHaveMonetaryAccountException customerAccountAlreadyHaveMonetaryAccountException) {
        this.errorCode = 10;
        this.errorDescription = "Customer account already has monetary account with that currency";
        this.errorMessage = customerAccountAlreadyHaveMonetaryAccountException.getMessage();
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

    public String getErrorDescription() {
        return errorDescription;
    }
}
