package com.matiasjuarez.errorhandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public abstract class BaseMapper<T extends Throwable> implements ExceptionMapper<T> {
    @Override
    public Response toResponse(T throwable) {
        return Response.status(getResponseStatus(throwable)).entity(getErrorMessage(throwable))
                .header("Content-Type", "application/json").build();
    }

    protected abstract Response.Status getResponseStatus(T throwable);
    protected abstract String getErrorMessage(T throwable);
}
