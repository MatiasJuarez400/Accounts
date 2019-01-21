package com.matiasjuarez.api;

import com.matiasjuarez.api.exception.InvalidCurrencyPairException;
import com.matiasjuarez.money.model.ExchangeRate;

import java.math.BigDecimal;

public interface ExchangeRateService {
    ExchangeRate getExchangeRate(String currencyPair) throws InvalidCurrencyPairException;
    BigDecimal getBidExchangeRate(String currencyPair) throws InvalidCurrencyPairException;
    BigDecimal getOfferExchangeRate(String currencyPair) throws InvalidCurrencyPairException;
}
