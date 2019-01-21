package com.matiasjuarez.exchangeRateService;

import com.matiasjuarez.exchangeRateService.exception.InvalidCurrencyPairException;
import com.matiasjuarez.money.ExchangeRate;

import java.math.BigDecimal;

public interface ExchangeRateService {
    ExchangeRate getExchangeRate(String currencyPair) throws InvalidCurrencyPairException;
    BigDecimal getBidExchangeRate(String currencyPair) throws InvalidCurrencyPairException;
    BigDecimal getOfferExchangeRate(String currencyPair) throws InvalidCurrencyPairException;
}
