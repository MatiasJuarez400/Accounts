package com.matiasjuarez.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Money {
    private BigDecimal amount;
    private Currency currency;
}
