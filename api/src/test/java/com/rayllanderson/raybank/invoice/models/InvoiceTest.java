package com.rayllanderson.raybank.invoice.models;

import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.invoice.models.inputs.ProcessInvoiceCredit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.rayllanderson.raybank.invoice.InvoiceUtils.bigDecimalOf;
import static com.rayllanderson.raybank.invoice.InvoiceUtils.create;
import static com.rayllanderson.raybank.invoice.InvoiceUtils.installment;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.CLOSED;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.NONE;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.OPEN;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.PAID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class InvoiceTest {

    @Test
    void shouldProcessInstallment() {
        final var dueDate = LocalDate.now().plusDays(15);
        Invoice invoice = create(dueDate, bigDecimalOf(0), OPEN);

        invoice.addInstallment(installment(bigDecimalOf(50)));

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(50));
        assertThat(invoice.getInstallments()).isNotEmpty().hasSize(1);
    }

    @Test
    void shouldReceiveParcialPaymentWhenIsNotPaymentDate() {
        final var dueDate = LocalDate.now().plusDays(15);
        Invoice invoice = create(dueDate, OPEN, installment(bigDecimalOf(25)), installment(bigDecimalOf(25)));
        final var creditInput = new ProcessInvoiceCredit(bigDecimalOf(40), InvoiceCreditType.INVOICE_PAYMENT, "", "tId", LocalDate.now());

        invoice.processCredit(creditInput);

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(10));
        assertThat(invoice.isPaid()).isFalse();
        assertThat(invoice.getCredits()).isNotEmpty().hasSize(1);
    }

    @Test
    void shouldReceivePaymentWhenIsPaymentDate() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = create(dueDate, OPEN, installment(bigDecimalOf(50)));
        final var creditInput = new ProcessInvoiceCredit(bigDecimalOf(50), InvoiceCreditType.INVOICE_PAYMENT, "", "tId", LocalDate.now());

        invoice.processCredit(creditInput);

        assertThat(invoice.getTotal()).isZero();
        assertThat(invoice.isPaid()).isTrue();
        assertThat(invoice.getCredits()).isNotEmpty().hasSize(1);
    }

    @Test
    void shouldNotReceiveParcialPaymentWhenIsPaymentDate() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = create(dueDate, CLOSED, installment(bigDecimalOf(50)));
        final var creditInput = new ProcessInvoiceCredit(bigDecimalOf(40), InvoiceCreditType.INVOICE_PAYMENT,"", "tId", LocalDate.now());

        assertThatExceptionOfType(UnprocessableEntityException.class)
                .isThrownBy(() -> invoice.processCredit(creditInput))
                .withMessage("Não é possível receber pagamento parcial para fatura fechada ou vencida. Total da fatura: 50.00");

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(50));
        assertThat(invoice.isPaid()).isFalse();
        assertThat(invoice.getCredits()).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenInvoiceIsPaid() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = create(dueDate, PAID, installment(bigDecimalOf(50)));
        final var creditInput = new ProcessInvoiceCredit(bigDecimalOf(40), InvoiceCreditType.INVOICE_PAYMENT, "", "tId", LocalDate.now());

        assertThatExceptionOfType(UnprocessableEntityException.class)
                .isThrownBy(() -> invoice.processCredit(creditInput))
                .withMessage("Não é possível receber pagamento para fatura já paga.");

        assertThat(invoice.isPaid()).isTrue();
        assertThat(invoice.getCredits()).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenAmmountIsGreaterThanInvoice() {
        final var dueDate = LocalDate.now().plusDays(5);
        Invoice invoice = create(dueDate, OPEN, installment(bigDecimalOf(10)));
        final var creditInput = new ProcessInvoiceCredit(bigDecimalOf(40), InvoiceCreditType.INVOICE_PAYMENT, "", "tId", LocalDate.now());

        assertThatExceptionOfType(UnprocessableEntityException.class)
                .isThrownBy(() -> invoice.processCredit(creditInput))
                .withMessage("O valor recebido é superior ao da fatura.");

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(10));
        assertThat(invoice.getCredits()).isEmpty();
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

    @Test
    void shouldReceiveCreditRefund() {
        final var dueDate = LocalDate.now();
        Invoice invoice = create(dueDate, OPEN, installment(bigDecimalOf(50)));
        final var creditInput = new ProcessInvoiceCredit(bigDecimalOf(25), InvoiceCreditType.REFUND, "", "tId", LocalDate.now());

        invoice.processCredit(creditInput);

        assertThat(invoice.getTotal()).isEqualTo(bigDecimalOf(25));
    }
}