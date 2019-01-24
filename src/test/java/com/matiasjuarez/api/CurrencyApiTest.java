package com.matiasjuarez.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.matiasjuarez.model.money.Currency;
import static org.junit.Assert.*;

import com.sun.research.ws.wadl.HTTPMethods;
import org.junit.Test;

import java.util.List;

public class CurrencyApiTest extends BaseApiTest {

    @Test
    public void executeGetCurrency_expectNotNullCurrencyObject() {
        String bodyContent = executeRequestAndExtractBodyResponse(HTTPMethods.GET,"/currencies/USD");

        Currency currency = mapBodyContentToObject(new TypeReference<Currency>() {}, bodyContent);

        assertNotNull(currency);
        assertEquals("USD", currency.getTicker());
        assertNotNull(currency.getName());
    }

    @Test
    public void executeGetCurrencies_expectNotEmptyList() {
        String bodyContent = executeRequestAndExtractBodyResponse(HTTPMethods.GET,"/currencies");

        List<Currency> currencies = mapBodyContentToObject(new TypeReference<List<Currency>>() {}, bodyContent);

        assertNotNull(currencies);
        assertTrue(currencies.size() > 0);
    }
}
