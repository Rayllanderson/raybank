package com.rayllanderson.raybank.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InstallmentUtil {

    public static BigDecimal calculateInstallmentValue(BigDecimal total, int installments) {
        return total.divide(BigDecimal.valueOf(installments), 2, RoundingMode.HALF_UP);
    }
    public static String createDescription(String description, int currentInstallment, int totalInstallment) {
        if (currentInstallment == 1 && totalInstallment == 1) return description;
        return String.format("%s %s/%s", description, currentInstallment, totalInstallment);
    }
}
