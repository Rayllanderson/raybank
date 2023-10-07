package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.installment.models.Installment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MathUtilsTest {

    @Test
    void shouldSumACollection() {
        final var collection = List.of(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN);

        final BigDecimal totalSum = MathUtils.sum(collection);

        assertThat(totalSum).isEqualTo(new BigDecimal(21));
    }

    @Test
    void shouldSumStream() {
        final var installments = List.of(new Installment(null, null, BigDecimal.TEN, null, null, null),
                new Installment(null, null, BigDecimal.TEN, null, null, null),
                new Installment(null, null, BigDecimal.ONE, null, null, null));

        final BigDecimal totalSum = MathUtils.sum(installments.stream().map(Installment::getValue));

        assertThat(totalSum).isEqualTo(new BigDecimal("21.00"));
    }

    @Test
    void shouldCalulatePercentage() {
        final var value = BigDecimal.TEN;
        final var total = BigDecimal.valueOf(100);

        final BigDecimal percentage = MathUtils.toPercentage(value, total);

        assertThat(percentage).isEqualTo(new BigDecimal("10.00"));
    }


    @Test
    void shouldCalulatePercentageSameValues() {
        final var value = BigDecimal.valueOf(50);
        final var total = BigDecimal.valueOf(50);

        final BigDecimal percentage = MathUtils.toPercentage(value, total);

        assertThat(percentage).isEqualTo(new BigDecimal("100.00"));
    }
}