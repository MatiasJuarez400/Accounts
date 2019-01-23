package com.matiasjuarez.api.transactionerror;

import com.matiasjuarez.api.ApiUtils;
import com.matiasjuarez.api.monetaryaccount.MonetaryAccountResource;
import com.matiasjuarez.model.monetaryaccount.transaction.TransactionError;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

import static com.matiasjuarez.api.transactionerror.TransactionErrorResource.TRANSACTION_ERRORS_PATH;

@Path(TRANSACTION_ERRORS_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionErrorResource {
    public static final String TRANSACTION_ERRORS_PATH = "/transactionerrors";

    @Inject
    private TransactionErrorService transactionErrorService;

    private static final String MONETARY_ACCOUNT_ID = "monetaryAccountId";
    private static final String CUSTOMER_ACCOUNT_ID = "customerAccountId";

    public TransactionErrorResource(TransactionErrorService transactionErrorService) {
        this.transactionErrorService = transactionErrorService;
    }

    public TransactionErrorResource() {}

    @GET
    public Response getTransactionErrors(
            @QueryParam(MONETARY_ACCOUNT_ID) Long monetaryAccountId,
            @QueryParam(CUSTOMER_ACCOUNT_ID) Long customerAccountId) throws SQLException {

        if (monetaryAccountId != null) {
            List<TransactionError> transactionErrors =
                    transactionErrorService.getMonetaryAccountTransactionErrors(monetaryAccountId);

            return ApiUtils.buildOkResponse(transactionErrors);
        } else if (customerAccountId != null ) {
            List<TransactionError> transactionErrors =
                    transactionErrorService.getCustomerAccountTransactionErrors(customerAccountId);

            return ApiUtils.buildOkResponse(transactionErrors);
        } else {
            throw new BadRequestException(
                    String.format("Values for %s or %s must be present", MONETARY_ACCOUNT_ID, CUSTOMER_ACCOUNT_ID)
            );
        }
    }
}
