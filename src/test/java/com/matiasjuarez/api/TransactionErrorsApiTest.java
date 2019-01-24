package com.matiasjuarez.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.matiasjuarez.model.customer.Customer;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.transaction.TransactionError;
import com.sun.research.ws.wadl.HTTPMethods;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionErrorsApiTest extends BaseApiTest {
    @Test
    public void executeGetTransactionsErrorsByMonetaryAccountId_expectNotEmptyList() {
        Customer customer1 = createCustomer("name1", "lastname1");
        CustomerAccount newCustomerAccount1 = createCustomerAccount("AR", customer1.getId());
        MonetaryAccount monetaryAccount1 = createMonetaryAccount(newCustomerAccount1.getId(),
                "USD", true, "500");

        Customer customer2 = createCustomer("name2", "lastname2");
        CustomerAccount newCustomerAccount2 = createCustomerAccount("AR", customer2.getId());
        MonetaryAccount monetaryAccount2 = createMonetaryAccount(newCustomerAccount2.getId(),
                "USD", true, "1000");

        int failTransactionsToCreate = 6;
        int failedTransactions = 0;
        for (int i = 0; i < failTransactionsToCreate; i++) {
            try {
                createTransaction(monetaryAccount1.getId(), monetaryAccount2.getId(), "1550");
            } catch (Exception e) {
                failedTransactions++;
            }
        }

        String bodyContent = executeRequestAndExtractBodyResponse(HTTPMethods.GET,
                "/transactionerrors?monetaryAccountId=" + monetaryAccount1.getId());
        List<TransactionError> transactionErrors =
                mapBodyContentToObject(new TypeReference<List<TransactionError>>() {}, bodyContent);

        assertNotNull(transactionErrors);
        assertEquals(failedTransactions, transactionErrors.size());
        assertEquals(failTransactionsToCreate, transactionErrors.size());
    }


}
