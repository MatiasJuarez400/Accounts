package com.matiasjuarez.api.transaction;

import com.matiasjuarez.api.ApiUtils;
import com.matiasjuarez.api.monetaryaccount.MonetaryAccountResource;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.monetaryaccount.transaction.Transaction;
import com.matiasjuarez.model.money.Money;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.matiasjuarez.api.transaction.TransactionResource.TRANSACTIONS_PATH;

@Path(TRANSACTIONS_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {
    public static final String TRANSACTIONS_PATH = MonetaryAccountResource.MONETARY_ACCOUNTS_PATH +
            "/{monetaryAccountId}/transactions";

    private static final String TARGET_ACCOUNT = "targetAccount";
    private static final String AMOUNT_TO_TRANSFER = "amountToTransfer";

    @Inject
    private TransactionService transactionService;

    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public TransactionResource() {}

    @GET
    public Response getMonetaryAccountTransactions(@PathParam("monetaryAccountId") Long monetaryAccountId) throws SQLException {
        List<Transaction> storedTransactions = transactionService.getTransactions(monetaryAccountId);

        return ApiUtils.buildOkResponse(storedTransactions);
    }

    @POST
    public Response createTransaction(@PathParam("monetaryAccountId") Long monetaryAccountId, Map<String, Object> requestBody) throws SQLException {
        ApiUtils.validateIfValuesArePresent(requestBody, TARGET_ACCOUNT, AMOUNT_TO_TRANSFER);

        Long idTarget = Long.valueOf(ApiUtils.convertRequestValueToInteger(TARGET_ACCOUNT, requestBody.get(TARGET_ACCOUNT)));
        Double amountToTransfer = ApiUtils.convertRequestValueToDouble(AMOUNT_TO_TRANSFER, requestBody.get(AMOUNT_TO_TRANSFER));

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setOriginMonetaryAccountId(monetaryAccountId);
        transactionDTO.setTargetMonetaryAccountId(idTarget);
        transactionDTO.setAmount(amountToTransfer);

        Transaction createdTransaction = transactionService.createTransaction(transactionDTO);

        return ApiUtils.buildCreatedResponse(createdTransaction);
    }
}
