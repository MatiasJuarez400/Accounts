package com.matiasjuarez.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccountStatus;
import com.sun.research.ws.wadl.HTTPMethods;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MonetaryAccountApiTest extends BaseApiTest {
    @Test
    public void executeGetMonetaryAccounts_expectMonetaryAccountListNotEmpty() {
        String responseBody = executeRequestAndExtractBodyResponse(HTTPMethods.GET, "/customeraccounts/1/monetaryaccounts");

        List<MonetaryAccount> monetaryAccountList = mapBodyContentToObject(new TypeReference<List<MonetaryAccount>>() {}, responseBody);

        assertNotNull(monetaryAccountList);
        assertTrue(monetaryAccountList.size() > 0);
    }

    @Test
    public void executeCreateMonetaryAccount_expectMonetaryAccountCreationSuccessful() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("currencyTicker", "USD");
        requestBody.put("statusActive", true);

        CloseableHttpResponse postResponse = executeRequest(HTTPMethods.POST, "/customeraccounts/1/monetaryaccounts", requestBody);

        assertEquals(201, postResponse.getStatusLine().getStatusCode());

        MonetaryAccount createdMonetaryAccount = mapBodyContentToObject(
                new TypeReference<MonetaryAccount>() {}, extractBodyResponse(postResponse));

        assertEquals("USD", createdMonetaryAccount.getAccountCurrency().getTicker());
        assertTrue(createdMonetaryAccount.isOperative());
    }

    @Test
    public void executeCreateMonetaryAccount_expectBadRequest() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("statusActive", true);

        CloseableHttpResponse postResponse = executeRequest(HTTPMethods.POST, "/customeraccounts/1/monetaryaccounts", requestBody);

        assertEquals(400, postResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void executeUpdateMonetaryAccount_expectMonetaryAccountUpdateSuccessful() {
        Map<String, Object> createRequestBody = new HashMap<>();
        createRequestBody.put("currencyTicker", "USD");
        createRequestBody.put("statusActive", true);

        CloseableHttpResponse postResponse = executeRequest(HTTPMethods.POST, "/customeraccounts/1/monetaryaccounts", createRequestBody);

        MonetaryAccount createdMonetaryAccount = mapBodyContentToObject(
                new TypeReference<MonetaryAccount>() {}, extractBodyResponse(postResponse));

        MonetaryAccountStatus beforeMonetaryAccountStatus = createdMonetaryAccount.getAccountStatus();

        Map<String, Object> updateRequestBody = new HashMap<>();
        updateRequestBody.put("statusActive", false);
        CloseableHttpResponse updateResponse = executeRequest(HTTPMethods.PUT,
                "/customeraccounts/1/monetaryaccounts/" + createdMonetaryAccount.getId(), updateRequestBody);

        MonetaryAccount updatedMonetaryAccount = mapBodyContentToObject(
                new TypeReference<MonetaryAccount>() {}, extractBodyResponse(updateResponse));

        assertTrue(beforeMonetaryAccountStatus == MonetaryAccountStatus.OPERATIVE);
        assertTrue(beforeMonetaryAccountStatus != updatedMonetaryAccount.getAccountStatus());
        assertTrue(updatedMonetaryAccount.getAccountStatus() == MonetaryAccountStatus.INACTIVE);
        assertFalse(updatedMonetaryAccount.isOperative());
    }

    @Test
    public void executeUpdateMonetaryAccount_expect404CustomerAccountNotFound() {
        Map<String, Object> updateRequestBody = new HashMap<>();
        updateRequestBody.put("statusActive", false);
        CloseableHttpResponse updateResponse = executeRequest(HTTPMethods.PUT,
                "/customeraccounts/10000/monetaryaccounts/1", updateRequestBody);

        assertEquals(404, updateResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void executeUpdateMonetaryAccount_expect403Forbidden_monetaryAccountNotFoundInCustomerAccount() {
        Map<String, Object> updateRequestBody = new HashMap<>();
        updateRequestBody.put("statusActive", false);
        CloseableHttpResponse updateResponse = executeRequest(HTTPMethods.PUT,
                "/customeraccounts/1/monetaryaccounts/10000", updateRequestBody);

        assertEquals(403, updateResponse.getStatusLine().getStatusCode());
    }
}
