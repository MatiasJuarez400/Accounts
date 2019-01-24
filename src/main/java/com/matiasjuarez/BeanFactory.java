package com.matiasjuarez;

import com.matiasjuarez.model.transaction.TransactionHandler;
import com.matiasjuarez.model.transaction.feecalculators.BasicFeeCalculatorStrategy;
import com.matiasjuarez.model.transaction.feecalculators.CurrencyConversionFeeCalcultorStrategy;
import com.matiasjuarez.model.transaction.feecalculators.FeeCalculatorStrategy;
import com.matiasjuarez.model.transaction.feecalculators.InternationalFeeCalculatorStrategy;
import com.matiasjuarez.model.money.MoneyConverter;
import com.matiasjuarez.model.money.exchangeRate.ExchangeRateService;
import com.matiasjuarez.model.money.exchangeRate.StoredExchangeRateService;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private Map<String, Object> beans;

    private static BeanFactory beanFactory;

    private BeanFactory() {
        beans = new HashMap<>();
    }

    public static BeanFactory getInstance() {
        if (beanFactory == null) {
            beanFactory = new BeanFactory();
        }

        return beanFactory;
    }

    public FeeCalculatorStrategy getFeeCalculatorStrategy() {
        if (beans.get(FeeCalculatorStrategy.class.getName()) == null) {
            InternationalFeeCalculatorStrategy internationalFeeCalculatorStrategy = new InternationalFeeCalculatorStrategy();
            CurrencyConversionFeeCalcultorStrategy currencyConversionFeeCalcultorStrategy = new CurrencyConversionFeeCalcultorStrategy();
            BasicFeeCalculatorStrategy basicFeeCalculatorStrategy = new BasicFeeCalculatorStrategy(internationalFeeCalculatorStrategy, currencyConversionFeeCalcultorStrategy);
            beans.put(FeeCalculatorStrategy.class.getName(), basicFeeCalculatorStrategy);
        }

        return (FeeCalculatorStrategy) beans.get(FeeCalculatorStrategy.class.getName());
    }

    public TransactionHandler getTransactionHandler() {
        if (!existInMap(TransactionHandler.class)) {
            beans.put(getKeyForClass(TransactionHandler.class),
                    new TransactionHandler(
                            getFeeCalculatorStrategy(),
                            getMoneyConverter()
                    ));
        }

        return (TransactionHandler) beans.get(getKeyForClass(TransactionHandler.class));
    }

    public MoneyConverter getMoneyConverter() {
        if (!existInMap(MoneyConverter.class)) {
            beans.put(getKeyForClass(MoneyConverter.class),
                    new MoneyConverter(
                            getExchangeRateService()
                    ));
        }

        return (MoneyConverter) beans.get(getKeyForClass(MoneyConverter.class));
    }

    public ExchangeRateService getExchangeRateService() {
        if (!existInMap(ExchangeRateService.class)) {
            beans.put(getKeyForClass(ExchangeRateService.class), new StoredExchangeRateService());
        }

        return (ExchangeRateService) beans.get(getKeyForClass(ExchangeRateService.class));
    }

    private boolean existInMap(Class clazz) {
        return beans.containsKey(getKeyForClass(clazz));
    }

    private String getKeyForClass(Class clazz) {
        return clazz.getName();
    }
}
