package com.matiasjuarez.api.transaction;

import com.matiasjuarez.api.ApiUtils;
import com.matiasjuarez.model.transaction.Transaction;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.matiasjuarez.api.transaction.TransactionResource.TRANSACTION_PATH;

@Path(TRANSACTION_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {
    public static final String TRANSACTION_PATH = "/transactions";

    private static final String ORIGIN_ACCOUNT = "originMonetaryAccount";
    private static final String TARGET_ACCOUNT = "targetMonetaryAccount";
    private static final String AMOUNT_TO_TRANSFER = "amountToTransfer";
    private static final String MONETARY_ACCOUNT_ID = "monetaryAccountId";
    private static final String CUSTOMER_ACCOUNT_ID = "customerAccountId";

    @Inject
    private TransactionService transactionService;

    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransactionResource() {}

    @GET
    public Response getMonetaryAccountTransactions(@QueryParam(MONETARY_ACCOUNT_ID) Long monetaryAccountId,
                                                   @QueryParam(CUSTOMER_ACCOUNT_ID) Long customerAccountId) throws SQLException {

        if (monetaryAccountId != null) {
            List<Transaction> storedTransactions = transactionService.getTransactionsForMonetaryAccount(monetaryAccountId);

            return ApiUtils.buildOkResponse(storedTransactions);
        } else if (customerAccountId != null) {
            List<Transaction> storedTransactions = transactionService.getTransactionsForCustomerAccount(customerAccountId);

            return ApiUtils.buildOkResponse(storedTransactions);
        } else {
            throw new BadRequestException(
                    String.format("Query param %s or %s must be present", MONETARY_ACCOUNT_ID, CUSTOMER_ACCOUNT_ID)
            );
        }

    }

    @POST
    public Response createTransaction(Map<String, Object> requestBody) throws Exception {
        ApiUtils.validateIfValuesArePresent(requestBody, ORIGIN_ACCOUNT, TARGET_ACCOUNT, AMOUNT_TO_TRANSFER);

        Long idOrigin = Long.valueOf(ApiUtils.convertRequestValueToInteger(ORIGIN_ACCOUNT, requestBody.get(ORIGIN_ACCOUNT)));
        Long idTarget = Long.valueOf(ApiUtils.convertRequestValueToInteger(TARGET_ACCOUNT, requestBody.get(TARGET_ACCOUNT)));
        BigDecimal amountToTransfer = ApiUtils.convertRequestValueToBigDecimal(AMOUNT_TO_TRANSFER, requestBody.get(AMOUNT_TO_TRANSFER));

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setOriginMonetaryAccountId(idOrigin);
        transactionDTO.setTargetMonetaryAccountId(idTarget);
        transactionDTO.setAmount(amountToTransfer);

        Transaction createdTransaction = transactionService.createTransaction(transactionDTO);

        return ApiUtils.buildCreatedResponse(createdTransaction);
    }
}
