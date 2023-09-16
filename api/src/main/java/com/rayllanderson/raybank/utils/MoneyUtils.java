package com.rayllanderson.raybank.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtils {

    public static BigDecimal from(BigDecimal b) {
        return b.setScale(2, RoundingMode.HALF_UP);
    }
}
