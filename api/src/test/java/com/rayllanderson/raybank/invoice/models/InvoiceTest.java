package com.rayllanderson.raybank.invoice.models;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.rayllanderson.raybank.invoice.InvoiceUtils.bigDecimalOf;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.CLOSED;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.NONE;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.OPEN;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.PAID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class InvoiceTest {

    @Test
    void shouldProcesstPayment() {
        final var dueDate = LocalDate.now().plusDays(15);
        Invoice invoice = create(dueDate, bigDecimalOf(0), OPEN);

        invoice.processPayment("amazon", bigDecimalOf(500), bigDecimalOf(50), LocalDateTime.parse("2023-04-16T14:45:00"));

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(50));
        assertThat(invoice.getInstallments()).isNotEmpty().hasSize(1);
    }

    @Test
    void shouldReceiveParcialPaymentWhenIsNotPaymentDate() {
        final var dueDate = LocalDate.now().plusDays(15);
        Invoice invoice = create(dueDate, bigDecimalOf(50), OPEN);

        invoice.processPayment(bigDecimalOf(40));

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(10));
        assertThat(invoice.isPaid()).isFalse();
    }

    @Test
    void shouldReceivePaymentWhenIsPaymentDate() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = create(dueDate, bigDecimalOf(50), OPEN);

        invoice.processPayment(bigDecimalOf(50));

        assertThat(invoice.getTotal()).isZero();
        assertThat(invoice.isPaid()).isTrue();
    }

    @Test
    void shouldNotReceiveParcialPaymentWhenIsPaymentDate() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = create(dueDate, bigDecimalOf(50), CLOSED);

        assertThatExceptionOfType(UnprocessableEntityException.class)
                .isThrownBy(() -> invoice.processPayment(bigDecimalOf(40)))
                .withMessage("Não é possível receber pagamento parcial para fatura fechada ou vencida. Total da fatura: 50.00");

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(50));
        assertThat(invoice.isPaid()).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenInvoiceIsPaid() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = create(dueDate, bigDecimalOf(10), PAID);

        assertThatExceptionOfType(UnprocessableEntityException.class)
                .isThrownBy(() -> invoice.processPayment(bigDecimalOf(40)))
                .withMessage("Não é possível receber pagamento para fatura já paga.");

        assertThat(invoice.isPaid()).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenAmmountIsGreaterThanInvoice() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = create(dueDate, bigDecimalOf(10), OPEN);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> invoice.processPayment(bigDecimalOf(40)))
                .withMessage("O valor recebido é superior ao da fatura.");

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(10));
    }

    @Test
    void shouldOpenInvoiceWhenNowIsBeforeClosingDate() {
        final var dueDate = LocalDate.now().plusDays(30);
        Invoice invoice = create(dueDate, BigDecimal.ZERO, NONE);

        invoice.open();

        assertThat(invoice.isOpen()).isTrue();
    }

    @Test
    void shouldNotOpenInvoiceWhenStatusIsNotNONE() {
        final var dueDate = LocalDate.now().plusDays(30);
        Invoice invoice = create(dueDate, BigDecimal.ZERO, CLOSED);

        invoice.open();

        assertThat(invoice.isOpen()).isFalse();
    }

    @Test
    void shouldCloseInvoiceWhenNowIsAfterOrEqualsClosingDate() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = create(dueDate, BigDecimal.TEN, OPEN);

        invoice.close();

        assertThat(invoice.isClosed()).isTrue();
    }

    @Test
    void shouldNotCloseInvoiceWhenIsNotOpen() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = create(dueDate, BigDecimal.TEN, NONE);

        invoice.close();

        assertThat(invoice.isClosed()).isFalse();
    }

    @Test
    void shouldOverdueInvoiceWhenNowIsAfterDueDate() {
        final var dueDate = LocalDate.now().minusDays(1);
        Invoice invoice = create(dueDate, BigDecimal.TEN, CLOSED);

        invoice.overdue();

        assertThat(invoice.isOverdue()).isTrue();
    }

    @Test
    void shouldNotOverdueInvoiceWhenIsPaid() {
        final var dueDate = LocalDate.now();
        Invoice invoice = create(dueDate, BigDecimal.TEN, PAID);

        invoice.overdue();

        assertThat(invoice.isOverdue()).isFalse();
        assertThat(invoice.isPaid()).isTrue();
    }

    @Test
    void shouldNotOverdueInvoiceWhenIsNotClosed() {
        final var dueDate = LocalDate.now();
        Invoice invoice = create(dueDate, BigDecimal.TEN, NONE);

        invoice.overdue();

        assertThat(invoice.isOverdue()).isFalse();
    }

    private static Invoice create(LocalDate dueDate, BigDecimal total, InvoiceStatus status) {
        return new Invoice("id", dueDate, dueDate.minusDays(6), total, status, new CreditCard(), new ArrayList<>());
    }
}