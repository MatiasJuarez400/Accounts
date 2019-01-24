package com.matiasjuarez.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.matiasjuarez.model.customer.Customer;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.transaction.Transaction;
import com.sun.research.ws.wadl.HTTPMethods;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TransactionApiTest extends BaseApiTest{
    @Test
    public void executeCreateTransaction_expectTransactionCreated_monetaryAccountFundsAffected() {
        Customer customer1 = createCustomer("name1", "lastname1");
        CustomerAccount newCustomerAccount1 = createCustomerAccount("AR", customer1.getId());
        MonetaryAccount monetaryAccount1 = createMonetaryAccount(newCustomerAccount1.getId(),
                "USD", true, "4000");

        Customer customer2 = createCustomer("name2", "lastname2");
        CustomerAccount newCustomerAccount2 = createCustomerAccount("AR", customer2.getId());
        MonetaryAccount monetaryAccount2 = createMonetaryAccount(newCustomerAccount2.getId(),
                "USD", true, "1000");

        Transaction transaction = createTransaction(monetaryAccount1.getId(), monetaryAccount2.getId(), "350");

        assertEquals("3650.00", transaction.getOriginFundsAfterTransaction());
        assertEquals("1350.00", transaction.getTargetFundsAfterTransaction());

        List<MonetaryAccount> monetaryAccountList1 = getMonetaryAccountsFromCustomerAccount(newCustomerAccount1.getId());
        List<MonetaryAccount> monetaryAccountList2 = getMonetaryAccountsFromCustomerAccount(newCustomerAccount2.getId());

        assertTrue(new BigDecimal("3650.00").compareTo(monetaryAccountList1.get(0).getFunds()) == 0);
        assertTrue(new BigDecimal("1350.00").compareTo(monetaryAccountList2.get(0).getFunds()) == 0);
    }

    @Test
    public void executeCreateTransaction_expectTransactionFail_insufficientFunds() {
        Customer customer1 = createCustomer("name1", "lastname1");
        CustomerAccount newCustomerAccount1 = createCustomerAccount("AR", customer1.getId());
        MonetaryAccount monetaryAccount1 = createMonetaryAccount(newCustomerAccount1.getId(),
                "USD", true, "500");

        Customer customer2 = createCustomer("name2", "lastname2");
        CustomerAccount newCustomerAccount2 = createCustomerAccount("AR", customer2.getId());
        MonetaryAccount monetaryAccount2 = createMonetaryAccount(newCustomerAccount2.getId(),
                "USD", true, "1000");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("originMonetaryAccount", monetaryAccount1.getId());
        requestBody.put("targetMonetaryAccount", monetaryAccount2.getId());
        requestBody.put("amountToTransfer", "3050");

        CloseableHttpResponse postResponse = executeRequest(HTTPMethods.POST, "/transactions", requestBody);

        assertEquals(409, postResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void executeGetTransactionsByMonetaryAccountId_expectNotEmptyList() {
        Customer customer1 = createCustomer("name1", "lastname1");
        CustomerAccount newCustomerAccount1 = createCustomerAccount("AR", customer1.getId());
        MonetaryAccount monetaryAccount1 = createMonetaryAccount(newCustomerAccount1.getId(),
                "USD", true, "4000");

        Customer customer2 = createCustomer("name2", "lastname2");
        CustomerAccount newCustomerAccount2 = createCustomerAccount("AR", customer2.getId());
        MonetaryAccount monetaryAccount2 = createMonetaryAccount(newCustomerAccount2.getId(),
                "USD", true, "1000");

        createTransaction(monetaryAccount1.getId(), monetaryAccount2.getId(), "350");
        createTransaction(monetaryAccount1.getId(), monetaryAccount2.getId(), "500");
        createTransaction(monetaryAccount2.getId(), monetaryAccount1.getId(), "200");

        String bodyContent = executeRequestAndExtractBodyResponse(HTTPMethods.GET,
                "/transactions?monetaryAccountId=" + monetaryAccount1.getId());
        List<Transaction> transactionList = mapBodyContentToObject(new TypeReference<List<Transaction>>() {}, bodyContent);

        assertNotNull(transactionList);
        assertEquals(3, transactionList.size());
    }

    private List<MonetaryAccount> getMonetaryAccountsFromCustomerAccount(Long customerAccountId) {
        String responseBody = executeRequestAndExtractBodyResponse(HTTPMethods.GET,
                "/customeraccounts/" + customerAccountId + "/monetaryaccounts");
        List<MonetaryAccount> monetaryAccountList = mapBodyContentToObject(new TypeReference<List<MonetaryAccount>>() {}, responseBody);

        return monetaryAccountList;
    }
}
