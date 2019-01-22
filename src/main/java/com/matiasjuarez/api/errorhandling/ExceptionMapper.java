package com.matiasjuarez.api.errorhandling;

import javax.ws.rs.core.Response;

public class ExceptionMapper extends BaseMapper<Exception> {
    @Override
    protected Response.Status getResponseStatus(Exception throwable) {
        return Response.Status.INTERNAL_SERVER_ERROR;
    }

    @Override
    protected String getErrorMessage(Exception throwable) {
        return new ErrorModel(throwable).toJson();
    }
}
