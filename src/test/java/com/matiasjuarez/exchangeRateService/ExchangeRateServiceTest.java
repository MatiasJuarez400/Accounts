package com.matiasjuarez.exchangeRateService;

import com.matiasjuarez.exchangeRateService.exception.InvalidCurrencyPairException;
import com.matiasjuarez.money.ExchangeRate;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class ExchangeRateServiceTest {
    public static final String USDUSD = "USDUSD";
    private static final String USDEUR = "USDEUR";
    private static final String BID_USD_EUR = "1.2139";
    private static final String OFF_USD_EUR = "0.7844";
    ExchangeRateService exchangeRateService;

    @Before
    public void setUp() {
        exchangeRateService = ExchangeRateServiceImpl.getInstance();
    }

    @Test
    public void testExchangeRate_validExchangeRate() {
        String currencyPair = USDEUR;
        ExchangeRate exchangeRate;
        exchangeRate = exchangeRateService.getExchangeRate(currencyPair);

        //Indirect bid and offer
        assert (exchangeRate.getBid()).equals(new BigDecimal(BID_USD_EUR));
        assert (exchangeRate.getOffer()).equals(new BigDecimal(OFF_USD_EUR));

        //Direct bid and offer
        assert (exchangeRateService.getBidExchangeRate(currencyPair)).equals(new BigDecimal(BID_USD_EUR));
        assert (exchangeRateService.getOfferExchangeRate(currencyPair)).equals(new BigDecimal(OFF_USD_EUR));
    }

    @Test(expected = InvalidCurrencyPairException.class)
    public void testExchangeRate_invalidCurrencyPair() {
        String currencyPair = USDUSD;
        exchangeRateService.getExchangeRate(currencyPair);
    }
}

