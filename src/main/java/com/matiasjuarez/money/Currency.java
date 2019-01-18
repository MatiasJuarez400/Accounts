package com.matiasjuarez.money;

import lombok.Data;

@Data
public class Currency {
    private String ticker;
    private String name;

    public Currency(String ticker, String name) {
        this.ticker = ticker;
        this.name = name;
    }
}
