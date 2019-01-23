package com.matiasjuarez.api.transactionerror;

import com.matiasjuarez.api.ApiUtils;
import com.matiasjuarez.api.monetaryaccount.MonetaryAccountResource;
import com.matiasjuarez.model.monetaryaccount.transaction.TransactionError;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

import static com.matiasjuarez.api.transactionerror.TransactionErrorResource.TRANSACTION_ERRORS_PATH;

@Path(TRANSACTION_ERRORS_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionErrorResource {
    public static final String TRANSACTION_ERRORS_PATH = MonetaryAccountResource.MONETARY_ACCOUNTS_PATH +
            "/{monetaryAccountId}/transactionerrors";

    @Inject
    private TransactionErrorService transactionErrorService;

    public TransactionErrorResource(TransactionErrorService transactionErrorService) {
        this.transactionErrorService = transactionErrorService;
    }

    public TransactionErrorResource() {}

    @GET
    public Response getTransactionErrorsForMonetaryAccount(
            @PathParam("monetaryAccountId") Long monetaryAccountId) throws SQLException {

        List<TransactionError> transactionErrors =
                transactionErrorService.getMonetaryAccountTransactionErrors(monetaryAccountId);

        return ApiUtils.buildOkResponse(transactionErrors);
    }
}
