package com.matiasjuarez.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matiasjuarez.config.server.ServerConfig;
import com.matiasjuarez.config.server.ServerLauncher;
import com.matiasjuarez.data.FakeDataLoader;
import com.matiasjuarez.model.customer.Customer;
import com.matiasjuarez.model.customer.CustomerAccount;
import com.matiasjuarez.model.monetaryaccount.MonetaryAccount;
import com.matiasjuarez.model.transaction.Transaction;
import com.sun.research.ws.wadl.HTTPMethods;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseApiTest {
    protected static ServerLauncher serverLauncher;
    protected static String BASE_URL = "http://localhost:" + ServerConfig.getInstance().getServerPort() +
            "/" + ServerConfig.getInstance().getServerBasePath();

    @BeforeClass
    public static void setup() {
        if (serverLauncher == null) {
            FakeDataLoader fakeDataLoader = new FakeDataLoader();
            fakeDataLoader.loadData();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    serverLauncher = new ServerLauncher();
                    serverLauncher.launch();
                }
            }).start();
        }

        int waitingTime = 500;
        int maxWaitingTime = 15000;
        int waitTime = 0;
        while ((serverLauncher == null || serverLauncher.getServer() == null ||
                serverLauncher.getServer().getState().equalsIgnoreCase("STARTING")) &&
                waitTime < maxWaitingTime) {
            try {
                Thread.sleep(waitingTime);
                waitTime += waitingTime;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected CloseableHttpResponse executeRequest(HTTPMethods httpMethod, String resourcePath, String requestBody) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String url = BASE_URL + resourcePath;
        try {
            switch (httpMethod) {
                case GET: {
                    return httpClient.execute(new HttpGet(url));
                }
                case PUT: {
                    HttpPut httpPut = new HttpPut(url);
                    httpPut.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
                    if (StringUtils.isNotEmpty(requestBody)) {
                        httpPut.setEntity(new StringEntity(requestBody));
                    }

                    return httpClient.execute(httpPut);
                }
                case POST: {
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
                    if (StringUtils.isNotEmpty(requestBody)) {
                        httpPost.setEntity(new StringEntity(requestBody));
                    }

                    return httpClient.execute(httpPost);
                }
                default: {
                    throw new IllegalArgumentException("Http method not supported");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected CloseableHttpResponse executeRequest(HTTPMethods httpMethod,
                                                   String resourcePath, Map<String, Object> requestBody) {

        return executeRequest(httpMethod, resourcePath, convertMapToJson(requestBody));
    }

    protected CloseableHttpResponse executeRequest(HTTPMethods httpMethod, String resourcePath) {
        return executeRequest(httpMethod, resourcePath, "");
    }

    protected String executeRequestAndExtractBodyResponse(
            HTTPMethods httpMethod, String resourcePath, String requestBody) {

        CloseableHttpResponse response = executeRequest(httpMethod, resourcePath, requestBody);

        return extractBodyResponse(response);
    }

    protected String executeRequestAndExtractBodyResponse(
            HTTPMethods httpMethod, String resourcePath, Map<String, Object> requestBody) {

        return executeRequestAndExtractBodyResponse(httpMethod, resourcePath, convertMapToJson(requestBody));
    }

    protected String executeRequestAndExtractBodyResponse(HTTPMethods httpMethod, String resourcePath) {
        return executeRequestAndExtractBodyResponse(httpMethod, resourcePath, "");
    }

    protected String extractBodyResponse(CloseableHttpResponse response) {
        try {
            String bodyContent = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            response.close();
            return bodyContent;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String convertMapToJson(Map<String, Object> aMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(aMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> T mapBodyContentToObject(TypeReference<T> typeReference, String bodyContent) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(bodyContent, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String convertObjectToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected Customer createCustomer(String name, String lastname) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("lastname", lastname);

        CloseableHttpResponse postResponse = executeRequest(HTTPMethods.POST, "/customers", requestBody);

        Customer createdCustomer = mapBodyContentToObject(
                new TypeReference<Customer>() {}, extractBodyResponse(postResponse));

        return createdCustomer;
    }

    protected CustomerAccount createCustomerAccount(String countryCode, Long customerId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("countryCode", countryCode);
        requestBody.put("customerId", customerId);

        CloseableHttpResponse postResponse = executeRequest(HTTPMethods.POST, "/customeraccounts", requestBody);

        CustomerAccount createdCustomerAccount = mapBodyContentToObject(
                new TypeReference<CustomerAccount>() {}, extractBodyResponse(postResponse));

        return createdCustomerAccount;
    }

    protected MonetaryAccount createMonetaryAccount(Long customerAccountId, String currencyTicker,
                                                    boolean isActive, String initialFunds) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("currencyTicker", currencyTicker);
        requestBody.put("statusActive", isActive);
        requestBody.put("initialFunds", initialFunds);

        CloseableHttpResponse postResponse = executeRequest(HTTPMethods.POST, "/customeraccounts/" + customerAccountId + "/monetaryaccounts", requestBody);

        MonetaryAccount createdMonetaryAccount = mapBodyContentToObject(
                new TypeReference<MonetaryAccount>() {}, extractBodyResponse(postResponse));

        return createdMonetaryAccount;
    }

    protected Transaction createTransaction(Long originMonetaryAccount, Long targetMonetaryAccount, String amountToTransfer) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("originMonetaryAccount", originMonetaryAccount);
        requestBody.put("targetMonetaryAccount", targetMonetaryAccount);
        requestBody.put("amountToTransfer", amountToTransfer);

        CloseableHttpResponse postResponse = executeRequest(HTTPMethods.POST, "/transactions", requestBody);

        Transaction createdCustomerAccount = mapBodyContentToObject(
                new TypeReference<Transaction>() {}, extractBodyResponse(postResponse));

        return createdCustomerAccount;
    }
}
