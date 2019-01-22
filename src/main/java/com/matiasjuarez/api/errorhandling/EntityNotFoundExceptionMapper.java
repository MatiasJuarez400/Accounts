package com.matiasjuarez.api.errorhandling;

import com.matiasjuarez.api.errorhandling.exceptions.EntityNotFoundException;

import javax.ws.rs.core.Response;

public class EntityNotFoundExceptionMapper extends BaseMapper<EntityNotFoundException> {
    @Override
    protected Response.Status getResponseStatus(EntityNotFoundException throwable) {
        return Response.Status.NOT_FOUND;
    }

    @Override
    protected String getErrorMessage(EntityNotFoundException throwable) {
        return new ErrorModel(throwable).toJson();
    }
}
