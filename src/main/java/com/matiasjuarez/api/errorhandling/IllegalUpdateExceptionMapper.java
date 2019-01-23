package com.matiasjuarez.api.errorhandling;

import com.matiasjuarez.api.errorhandling.exceptions.IllegalUpdateException;

import javax.ws.rs.core.Response;

public class IllegalUpdateExceptionMapper extends BaseMapper<IllegalUpdateException> {
    @Override
    protected Response.Status getResponseStatus(IllegalUpdateException throwable) {
        return Response.Status.FORBIDDEN;
    }

    @Override
    protected String getErrorMessage(IllegalUpdateException throwable) {
        return new ErrorModel(throwable).toJson();
    }
}
