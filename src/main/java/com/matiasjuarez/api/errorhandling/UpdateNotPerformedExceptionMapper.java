package com.matiasjuarez.api.errorhandling;

import com.matiasjuarez.api.errorhandling.exceptions.UpdateNotPerformedException;

import javax.ws.rs.core.Response;

public class UpdateNotPerformedExceptionMapper extends BaseMapper<UpdateNotPerformedException> {
    @Override
    protected Response.Status getResponseStatus(UpdateNotPerformedException throwable) {
        return Response.Status.CONFLICT;
    }

    @Override
    protected String getErrorMessage(UpdateNotPerformedException throwable) {
        return new ErrorModel(throwable).toJson();
    }
}
