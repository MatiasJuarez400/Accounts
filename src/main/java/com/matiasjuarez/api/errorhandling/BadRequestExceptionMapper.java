package com.matiasjuarez.api.errorhandling;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

public class BadRequestExceptionMapper extends BaseMapper<BadRequestException> {
    @Override
    protected Response.Status getResponseStatus(BadRequestException throwable) {
        return Response.Status.BAD_REQUEST;
    }

    @Override
    protected String getErrorMessage(BadRequestException throwable) {
        return new ErrorModel(throwable).toJson();
    }
}
