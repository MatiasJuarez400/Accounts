package com.matiasjuarez.api;

import com.matiasjuarez.api.exception.InvalidCurrencyPairException;
import com.matiasjuarez.money.ExchangeRate;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class ExchangeRateServiceImpl implements ExchangeRateService {
    public static final int BID = 0;
    public static final int OFFER = 1;
    private Properties exchangeRateValues = new Properties();
    private static final String EXCHANGE_RATE_PROPERTIES = "exchangeRates.properties";
    static private ExchangeRateServiceImpl exchangeRateService;

    private ExchangeRateServiceImpl() {
        try (final InputStream stream = this.getClass().getClassLoader().
                getResourceAsStream(EXCHANGE_RATE_PROPERTIES)) {
            exchangeRateValues.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("There was a problem while trying to load default currency values" + e);
        }
    }

    public static ExchangeRateServiceImpl getInstance() {
        if (null == exchangeRateService) {
            exchangeRateService = new ExchangeRateServiceImpl();
        }
        return exchangeRateService;
    }

    @Override
    public ExchangeRate getExchangeRate(String currencyPair) throws InvalidCurrencyPairException {
        if (StringUtils.isEmpty(currencyPair)) {
            throw new IllegalArgumentException("it's impossible to get an exchange rate for an empty currencyPair");
        }

        String values = exchangeRateValues.getProperty(currencyPair);
        if (StringUtils.isEmpty(values)) {
            throw new InvalidCurrencyPairException(currencyPair);
        }
        String bidOffer[] = values.split(",");

        return new ExchangeRate(currencyPair, new BigDecimal(bidOffer[BID]), new BigDecimal(bidOffer[OFFER]));
    }

    @Override
    public BigDecimal getBidExchangeRate(String currencyPair) throws InvalidCurrencyPairException {
        return getExchangeRate(currencyPair).getBid();
    }

    @Override
    public BigDecimal getOfferExchangeRate(String currencyPair) throws InvalidCurrencyPairException {
        return getExchangeRate(currencyPair).getOffer();
    }
}
