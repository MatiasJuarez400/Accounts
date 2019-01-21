package com.matiasjuarez.api.errorhandling;

import javax.ws.rs.core.Response;

public class RuntimeExceptionMapper extends BaseMapper<RuntimeException> {
    @Override
    protected Response.Status getResponseStatus(RuntimeException e) {
        return Response.Status.INTERNAL_SERVER_ERROR;
    }

    @Override
    protected String getErrorMessage(RuntimeException e) {
        return new ErrorModel(e).toJson();
    }
}
