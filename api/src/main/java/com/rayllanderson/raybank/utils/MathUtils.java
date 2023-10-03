package com.rayllanderson.raybank.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Stream;

import static java.math.RoundingMode.HALF_UP;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathUtils {

    /**
     * @return A porcentagem a partir de um valor. Exemplo: 50 reais é quantos % de 50 reais? return 100%
     */
    public static BigDecimal toPercentage(final BigDecimal value, final BigDecimal total) {
        return (value.divide(total).multiply(new BigDecimal(100))).setScale(2, HALF_UP);
    }

    /**
     * @return O valor a partir de uma porcentagem. Exemplo: 50% de 50 reais é quanto? % return 25 reais
     */
    public static BigDecimal fromPercentage(final BigDecimal percentage, final BigDecimal total) {
        return total.multiply(percentage.divide(new BigDecimal(100))).setScale(2, HALF_UP);
    }

    public static BigDecimal sum(final Collection<BigDecimal> values) {
        return sum(values.stream());
    }

    public static BigDecimal sum(final Stream<BigDecimal> values) {
        return values.reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
