package com.matiasjuarez.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.matiasjuarez.model.customer.Customer;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.sun.research.ws.wadl.HTTPMethods;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CustomerAccountApiTest extends BaseApiTest {

    @Test
    public void executeGetCustomerAccount_expectCustomerAccountNotNull() {
        Customer newCustomer = createCustomer("aName", "aLastName");
        CustomerAccount newCustomerAccount = createCustomerAccount("AR", newCustomer.getId());

        String responseBody = executeRequestAndExtractBodyResponse(
                HTTPMethods.GET, "/customeraccounts/" + newCustomerAccount.getId());

        CustomerAccount retrieved = mapBodyContentToObject(new TypeReference<CustomerAccount>() {}, responseBody);

        assertNotNull(retrieved);
        assertNotNull(retrieved.getBaseCountry());
        assertNotNull(retrieved.getCustomer());
        assertNotNull(retrieved.getMonetaryAccounts());
        assertTrue(newCustomerAccount.getId() == retrieved.getId());
    }

    @Test
    public void executeGetCustomerAccount_expect404() {
        CloseableHttpResponse responseBody = executeRequest(HTTPMethods.GET, "/customeraccounts/100000");

        assertEquals(404, responseBody.getStatusLine().getStatusCode());
    }

    @Test
    public void executeGetCustomerAccounts_expectCustomerAccountListNotEmpty() {
        Customer newCustomer = createCustomer("aName", "aLastName");
        CustomerAccount newCustomerAccount = createCustomerAccount("AR", newCustomer.getId());

        String responseBody = executeRequestAndExtractBodyResponse(HTTPMethods.GET, "/customeraccounts");

        List<CustomerAccount> customerAccountList = mapBodyContentToObject(new TypeReference<List<CustomerAccount>>() {}, responseBody);

        assertNotNull(customerAccountList);
        assertTrue(customerAccountList.size() > 0);
    }

    @Test
    public void executeCreateCustomerAccount_expectCustomerAccountCreationSuccessful() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("countryCode", "GB");
        requestBody.put("customerId", 1);

        CloseableHttpResponse postResponse = executeRequest(HTTPMethods.POST, "/customeraccounts", requestBody);

        assertEquals(201, postResponse.getStatusLine().getStatusCode());

        CustomerAccount createdCustomerAccount = mapBodyContentToObject(
                new TypeReference<CustomerAccount>() {}, extractBodyResponse(postResponse));

        assertEquals("GB", createdCustomerAccount.getBaseCountry().getCode());
        assertTrue(1L == createdCustomerAccount.getCustomer().getId());
    }
}
