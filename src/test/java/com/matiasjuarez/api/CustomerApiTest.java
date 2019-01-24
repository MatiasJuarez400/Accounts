package com.matiasjuarez.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.matiasjuarez.model.customer.Customer;
import static org.junit.Assert.*;

import com.sun.research.ws.wadl.HTTPMethods;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerApiTest extends BaseApiTest {

    @Test
    public void executeGetCustomer_expectNotNullCustomer() {
        String responseBody = executeRequestAndExtractBodyResponse(HTTPMethods.GET,"/customers/1");

        Customer customer = mapBodyContentToObject(new TypeReference<Customer>() {}, responseBody);

        assertNotNull(customer);
        assertTrue(1L == customer.getId());
        assertNotNull(customer.getLastname());
        assertNotNull(customer.getName());
    }

    @Test
    public void executeGetCustomer_expect404() throws IOException {
        CloseableHttpResponse response = executeRequest(HTTPMethods.GET, "/customers/1000");

        assertEquals(404, response.getStatusLine().getStatusCode());

        response.close();
    }

    @Test
    public void executeCreateCustomer_expectCustomerCreatedSuccessfully() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Giselle");
        requestBody.put("lastname", "Bachman");

        CloseableHttpResponse postResponse = executeRequest(HTTPMethods.POST, "/customers", requestBody);

        assertEquals(201, postResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void executeUpdateCustomer_expectCustomerWithUpdatedData() {
        String customerBeforeString = executeRequestAndExtractBodyResponse(HTTPMethods.GET, "/customers/1");

        Customer customerBefore = mapBodyContentToObject(new TypeReference<Customer>() {}, customerBeforeString);

        Customer customerUpdateData = new Customer("AnUpdatedName", "AnUpdatedLastName");

        CloseableHttpResponse updateResponse =
                executeRequest(HTTPMethods.PUT, "/customers/1", convertObjectToJson(customerUpdateData));

        assertEquals(200, updateResponse.getStatusLine().getStatusCode());

        String customerAfterString = executeRequestAndExtractBodyResponse(HTTPMethods.GET, "/customers/1");

        Customer customerAfter = mapBodyContentToObject(new TypeReference<Customer>() {}, customerAfterString);

        assertEquals(customerBefore.getId(), customerAfter.getId());
        assertNotEquals(customerBefore.getName(), customerAfter.getName());
        assertNotEquals(customerBefore.getLastname(), customerAfter.getLastname());

        assertEquals("AnUpdatedName", customerAfter.getName());
        assertEquals("AnUpdatedLastName", customerAfter.getLastname());
    }
}
