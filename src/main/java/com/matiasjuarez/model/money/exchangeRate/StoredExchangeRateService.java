package com.matiasjuarez.model.money.exchangeRate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.Charset;

public class StoredExchangeRateService implements ExchangeRateService {
    private static ExchangeRate[] exchangeRates = null;

    @Override
    public ExchangeRate getExchangeRate(String tickerOrigin, String tickerTarget) {
        if (exchangeRates == null) {
            try {
                loadExchangeRates();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        ExchangeRate exchangeRateToUse = null;
        for (ExchangeRate exchangeRate : exchangeRates) {
            if ((exchangeRate.getTickerOrigin().equalsIgnoreCase(tickerOrigin) &&
                    exchangeRate.getTickerTarget().equalsIgnoreCase(tickerTarget))
                || (exchangeRate.getTickerOrigin().equalsIgnoreCase(tickerTarget) &&
                    exchangeRate.getTickerTarget().equalsIgnoreCase(tickerOrigin))
            ) {
                exchangeRateToUse = exchangeRate;
            }
        }

        if (exchangeRateToUse == null) {
            throw new IllegalStateException(
                    String.format("Could not find suitable exchange rate for [%s] and [%s]", tickerOrigin, tickerTarget)
            );
        }

        return exchangeRateToUse;
    }

    private void loadExchangeRates() throws Exception {
        String jsonContent;

        try(InputStream inputStream = this.getClass().
                getClassLoader().getResourceAsStream("data/exchangeRates.json")) {

            jsonContent = IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        }

        ObjectMapper objectMapper = new ObjectMapper();

        exchangeRates = objectMapper.readValue(jsonContent, ExchangeRate[].class);
    }
}
