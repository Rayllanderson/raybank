package com.rayllanderson.raybank.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;


class CreditCardTest {

    @Test
    void shouldProcessInvoices() {
        final var creditCard = CreditCard.create(1L, BigDecimal.valueOf(5000), 1, YearMonth.now().plusYears(2), 1, null);

        creditCard.processInvoice(BigDecimal.valueOf(150), 3, "alibaba", LocalDateTime.parse("2023-09-01T18:40:00"));
        creditCard.processInvoice(BigDecimal.valueOf(100), 2, "amazon", LocalDateTime.parse("2023-09-01T20:41:37"));

        assertThat(creditCard.getInvoices()).isNotEmpty().hasSize(3);
        final var invoices = new ArrayList<>(creditCard.getInvoices());
        Collections.sort(invoices);
        final var invoice1 = invoices.get(0);
        assertThat(invoice1.getClosingDate()).isEqualTo(LocalDate.parse("2023-09-26"));
        assertThat(invoice1.getDueDate()).isEqualTo(LocalDate.parse("2023-10-02"));
        assertThat(invoice1.getTotal()).isEqualTo(new BigDecimal("100.00"));
        assertThat(invoice1.getInstallments()).isNotEmpty().hasSize(2);
        assertThat(invoice1.getInstallments().get(0).getDescription()).isEqualTo("alibaba 1/3");
        assertThat(invoice1.getInstallments().get(1).getDescription()).isEqualTo("amazon 1/2");
        final var invoice2 = invoices.get(1);
        assertThat(invoice2.getClosingDate()).isEqualTo(LocalDate.parse("2023-10-26"));
        assertThat(invoice2.getDueDate()).isEqualTo(LocalDate.parse("2023-11-01"));
        assertThat(invoice2.getTotal()).isEqualTo(new BigDecimal("100.00"));
        assertThat(invoice2.getInstallments()).isNotEmpty().hasSize(2);
        assertThat(invoice2.getInstallments().get(0).getDescription()).isEqualTo("alibaba 2/3");
        assertThat(invoice2.getInstallments().get(1).getDescription()).isEqualTo("amazon 2/2");
        final var invoice3 = invoices.get(2);
        assertThat(invoice3.getClosingDate()).isEqualTo(LocalDate.parse("2023-11-25"));
        assertThat(invoice3.getDueDate()).isEqualTo(LocalDate.parse("2023-12-01"));
        assertThat(invoice3.getTotal()).isEqualTo(new BigDecimal("50.00"));
        assertThat(invoice3.getInstallments()).isNotEmpty().hasSize(1);
        assertThat(invoice3.getInstallments().get(0).getDescription()).isEqualTo("alibaba 3/3");
    }

    @Test
    void shouldThrowExceptionWhenInvoiceDateIsNotOnRange() {
        final var creditCard = CreditCard.create(1L, BigDecimal.valueOf(5000), 1, YearMonth.now().plusYears(2), 1, null);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> creditCard.processInvoice(
                        BigDecimal.valueOf(100),
                        2,
                        "alibaba",
                        LocalDateTime.parse("2023-02-01T20:41:37")))
                .withMessage("'ocurredOn' must not be before current invoice closing date");
    }

    @Test
    void shouldThrowExceptionWhenOcurrenceDateIsFuture() {
        final var creditCard = CreditCard.create(1L, BigDecimal.valueOf(5000), 1, YearMonth.now().plusYears(2), 1, null);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> creditCard.processInvoice(
                        BigDecimal.valueOf(100),
                        2,
                        "alibaba",
                        LocalDateTime.parse("2099-02-01T20:41:37")))
                .withMessage("'ocurredOn' must not be in the future");
    }
}