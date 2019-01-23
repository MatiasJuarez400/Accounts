package com.matiasjuarez.api.errorhandling;

import com.matiasjuarez.api.errorhandling.exceptions.InconsistentDataException;

import javax.ws.rs.core.Response;

public class InconsistentDataExceptionMapper extends BaseMapper<InconsistentDataException> {
    @Override
    protected Response.Status getResponseStatus(InconsistentDataException throwable) {
        return Response.Status.CONFLICT;
    }

    @Override
    protected String getErrorMessage(InconsistentDataException throwable) {
        return new ErrorModel(throwable).toJson();
    }
}
