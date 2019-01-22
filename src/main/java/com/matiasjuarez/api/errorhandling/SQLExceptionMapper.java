package com.matiasjuarez.api.errorhandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;

@Provider
public class SQLExceptionMapper extends BaseMapper<SQLException> {
    @Override
    protected Response.Status getResponseStatus(SQLException throwable) {
        return Response.Status.INTERNAL_SERVER_ERROR;
    }

    @Override
    protected String getErrorMessage(SQLException throwable) {
        return new ErrorModel(throwable).toJson();
    }
}
