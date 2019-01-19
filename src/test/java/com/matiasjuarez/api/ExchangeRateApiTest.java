package com.matiasjuarez.api;

import com.matiasjuarez.money.ExchangeRate;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class ExchangeRateApiTest {
    private Properties exchangeRateValues = new Properties();
    private static final String USDEUR = "USDEUR";
    private static final String EXCHANGE_RATE_PROPERTIES = "exchangeRates.properties";
    private static final int BID = 0;
    private static final int OFFER = 1;
    private static final String BID_USD_EUR = "1.2139";
    private static final String OFF_USD_EUR = "0.7844";

    @Before
    public void setUp() {
        try (final InputStream stream = this.getClass().getClassLoader().
                getResourceAsStream(EXCHANGE_RATE_PROPERTIES)) {
            exchangeRateValues.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testExchangeRate_EUR_USD() {
        String currencyPair = USDEUR;
        ExchangeRate exchangeRate;
        String bidOffer[] = exchangeRateValues.getProperty(currencyPair).split(",");
        exchangeRate = new ExchangeRate(currencyPair, new BigDecimal(bidOffer[BID]), new BigDecimal(bidOffer[OFFER]));

        assert (exchangeRate.getBid()).equals(new BigDecimal(BID_USD_EUR));
        assert (exchangeRate.getOffer()).equals(new BigDecimal(OFF_USD_EUR));
    }
}

