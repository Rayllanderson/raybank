package com.rayllanderson.raybank.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SumUtils {

    public static BigDecimal sum(final Collection<BigDecimal> values) {
        return sum(values.stream());
    }

    public static BigDecimal sum(final Stream<BigDecimal> values) {
        return values.reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
