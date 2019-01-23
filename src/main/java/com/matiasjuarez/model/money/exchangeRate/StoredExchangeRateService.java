package com.matiasjuarez.model.money.exchangeRate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class StoredExchangeRateService implements ExchangeRateService {
    private static List<ExchangeRate> exchangeRates = null;

    @Override
    public ExchangeRate getExchangeRate(String tickerOrigin, String tickerTarget) {
        if (exchangeRates == null) {
            try {
                loadExchangeRates();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (tickerOrigin.equalsIgnoreCase(tickerTarget)) {
            return buildSameCurrencyExchangeRate(tickerOrigin);
        }

        ExchangeRate exchangeRateToUse = null;
        for (ExchangeRate exchangeRate : exchangeRates) {
            if ((exchangeRate.getTickerOrigin().equalsIgnoreCase(tickerOrigin) &&
                    exchangeRate.getTickerTarget().equalsIgnoreCase(tickerTarget))
                || (exchangeRate.getTickerOrigin().equalsIgnoreCase(tickerTarget) &&
                    exchangeRate.getTickerTarget().equalsIgnoreCase(tickerOrigin))
            ) {
                exchangeRateToUse = exchangeRate;
                break;
            }
        }

        if (exchangeRateToUse == null) {
            throw new IllegalStateException(
                    String.format("Could not find suitable exchange rate for [%s] and [%s]", tickerOrigin, tickerTarget)
            );
        }

        return exchangeRateToUse;
    }

    private ExchangeRate buildSameCurrencyExchangeRate(String ticker) {
        return new ExchangeRate(ticker, ticker, BigDecimal.ONE);
    }

    private void loadExchangeRates() throws Exception {
        String jsonContent;

        try(InputStream inputStream = this.getClass().
                getClassLoader().getResourceAsStream("data/exchangeRates.json")) {

            jsonContent = IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        }

        ObjectMapper objectMapper = new ObjectMapper();

        ExchangeRateRaw[] exchangeRateRaws = objectMapper.readValue(jsonContent, ExchangeRateRaw[].class);

        exchangeRates = new ArrayList<>();
        for (ExchangeRateRaw exchangeRateRaw : exchangeRateRaws) {
            exchangeRates.add(new ExchangeRate(
                    exchangeRateRaw.getTickerOrigin(),
                    exchangeRateRaw.getTickerTarget(),
                    new BigDecimal(exchangeRateRaw.getRate())));
        }
    }
}
