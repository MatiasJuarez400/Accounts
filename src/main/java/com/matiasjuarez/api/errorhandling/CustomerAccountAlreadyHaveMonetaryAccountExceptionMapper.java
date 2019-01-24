package com.matiasjuarez.api.errorhandling;

import com.matiasjuarez.api.errorhandling.exceptions.CustomerAccountAlreadyHaveMonetaryAccountException;

import javax.ws.rs.core.Response;

public class CustomerAccountAlreadyHaveMonetaryAccountExceptionMapper
        extends BaseMapper<CustomerAccountAlreadyHaveMonetaryAccountException> {
    @Override
    protected Response.Status getResponseStatus(CustomerAccountAlreadyHaveMonetaryAccountException throwable) {
        return Response.Status.CONFLICT;
    }

    @Override
    protected String getErrorMessage(CustomerAccountAlreadyHaveMonetaryAccountException throwable) {
        return new ErrorModel(throwable).toJson();
    }
}
