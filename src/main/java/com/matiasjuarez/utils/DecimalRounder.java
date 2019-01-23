package com.matiasjuarez.utils;

import java.math.BigDecimal;

public class DecimalRounder {
    public static BigDecimal round(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
