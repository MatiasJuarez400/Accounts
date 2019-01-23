package com.matiasjuarez.api.errorhandling;

import com.matiasjuarez.api.errorhandling.exceptions.InvalidTransactionException;

import javax.ws.rs.core.Response;

public class InvalidTransactionExceptionMapper extends BaseMapper<InvalidTransactionException> {
    @Override
    protected Response.Status getResponseStatus(InvalidTransactionException throwable) {
        return Response.Status.CONFLICT;
    }

    @Override
    protected String getErrorMessage(InvalidTransactionException throwable) {
        return new ErrorModel(throwable).toJson();
    }
}
