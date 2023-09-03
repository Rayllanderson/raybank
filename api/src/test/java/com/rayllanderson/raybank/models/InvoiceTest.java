package com.rayllanderson.raybank.models;

import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.rayllanderson.raybank.models.InvoiceStatus.NONE;
import static com.rayllanderson.raybank.models.InvoiceStatus.PAID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class InvoiceTest {

    @Test
    void shouldReceiveParcialPaymentWhenIsNotPaymentDate() {
        Invoice invoice = Invoice.create(parse("2023-05-06"));
        invoice.processPayment("amazon", bigDecimalOf(500), bigDecimalOf(50), LocalDateTime.parse("2023-04-16T14:45:00"));

        invoice.receivePayment(bigDecimalOf(40));

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(10));
        assertThat(invoice.isPaid()).isFalse();
    }

    @Test
    void shouldReceivePaymentWhenIsPaymentDate() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = new Invoice("id", dueDate, dueDate.minusDays(6), bigDecimalOf(50), NONE, new ArrayList<>());

        invoice.receivePayment(bigDecimalOf(50));

        assertThat(invoice.getTotal()).isZero();
        assertThat(invoice.isPaid()).isTrue();
    }

    @Test
    void shouldNotReceiveParcialPaymentWhenIsPaymentDate() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = new Invoice("id", dueDate, dueDate.minusDays(6), bigDecimalOf(50), NONE, new ArrayList<>());

        assertThatExceptionOfType(UnprocessableEntityException.class)
                .isThrownBy(() -> invoice.receivePayment(bigDecimalOf(40)))
                .withMessage("Não é possível receber pagamento parcial para fatura fechada.");

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(50));
        assertThat(invoice.isPaid()).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenInvoiceIsPaid() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = new Invoice("id", dueDate, dueDate.minusDays(6), bigDecimalOf(10), PAID, new ArrayList<>());

        assertThatExceptionOfType(UnprocessableEntityException.class)
                .isThrownBy(() -> invoice.receivePayment(bigDecimalOf(40)))
                .withMessage("Não é possível receber pagamento para fatura já paga.");

        assertThat(invoice.isPaid()).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenAmmountIsGreaterThanInvoice() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = new Invoice("id", dueDate, dueDate.minusDays(6), bigDecimalOf(10), NONE, new ArrayList<>());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> invoice.receivePayment(bigDecimalOf(40)))
                .withMessage("O valor recebido é superior ao da fatura.");

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(10));
    }

    private static BigDecimal bigDecimalOf(long o) {
        return BigDecimal.valueOf(o);
    }

    private static LocalDate parse(String date) {
        return LocalDate.parse(date);
    }
}