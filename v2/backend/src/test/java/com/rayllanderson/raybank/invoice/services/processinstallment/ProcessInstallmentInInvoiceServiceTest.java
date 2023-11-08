package com.rayllanderson.raybank.invoice.services.processinstallment;

import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.installment.repository.InstallmentPlanGateway;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Debit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.rayllanderson.raybank.invoice.InvoiceUtils.bigDecimalOf;
import static com.rayllanderson.raybank.invoice.InvoiceUtils.create;
import static com.rayllanderson.raybank.invoice.InvoiceUtils.createPlan;
import static com.rayllanderson.raybank.invoice.InvoiceUtils.installment;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.NONE;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.OPEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessInstallmentInInvoiceServiceTest {

    @Mock
    private InvoiceGateway invoiceGateway;
    @Mock
    private InstallmentPlanGateway planGateway;
    @Mock
    private TransactionGateway transactionGateway;
    @InjectMocks
    private ProcessInstallmentInInvoiceService processInstallmentInInvoiceService;

    @Test
    void shouldProcessInvoices() {
        when(invoiceGateway.getDayOfDueDateByCardId("cId")).thenReturn(1);
        when(invoiceGateway.findAllByCardIdAndStatus(anyString(), any())).thenReturn(Collections.emptyList());
        final var expectedOutput = createPlan(bigDecimalOf(150),
                bigDecimalOf(50),
                installment(LocalDate.now()),
                installment(LocalDate.now().plusMonths(1)),
                installment(LocalDate.now().plusMonths(2)));
        when(planGateway.findById(any())).thenReturn(expectedOutput);
        when(transactionGateway.findById("transactionId")).thenReturn(createTransaction(LocalDateTime.now()));
        final var input = new ProcessInstallmentInvoiceInput("transactionId");

        final var invoices = processInstallmentInInvoiceService.processInvoice(input);

        final var invoice1 = invoices.get(0);
        assertThat(invoice1.getTotal()).isEqualTo(new BigDecimal("50.00"));
        assertThat(invoice1.getInstallments()).isNotEmpty().hasSize(1);
        final var invoice2 = invoices.get(1);
        assertThat(invoice2.getTotal()).isEqualTo(new BigDecimal("50.00"));
        assertThat(invoice2.getInstallments()).isNotEmpty().hasSize(1);
        final var invoice3 = invoices.get(2);
        assertThat(invoice3.getTotal()).isEqualTo(new BigDecimal("50.00"));
        assertThat(invoice3.getInstallments()).isNotEmpty().hasSize(1);
    }

    @Test
    void shouldReuseExistingInvoices() {
        LocalDate now = LocalDate.now();
        LocalDate dueDate = now.plusDays(10);
        final var savedInvoices = List.of(
                create(dueDate, OPEN, installment("alibaba 1/3", bigDecimalOf(50.00), now)),
                create(dueDate.plusMonths(1), NONE, installment("alibaba 2/3", bigDecimalOf(50.00), now.plusMonths(1))),
                create(dueDate.plusMonths(2), NONE, installment("alibaba 3/3", bigDecimalOf(50.00), now.plusMonths(2)))
        );
        when(invoiceGateway.getDayOfDueDateByCardId("cId")).thenReturn(1);
        when(invoiceGateway.findAllByCardIdAndStatus(any(), any())).thenReturn(savedInvoices);
        when(transactionGateway.findById("transactionId")).thenReturn(createTransaction(LocalDateTime.now()));
        final var expectedOutput = createPlan(bigDecimalOf(150), bigDecimalOf(50),
                installment(now),
                installment(now.plusMonths(1)));
        when(planGateway.findById(any())).thenReturn(expectedOutput);
        final var input = new ProcessInstallmentInvoiceInput("transactionId");

        final var invoices = processInstallmentInInvoiceService.processInvoice(input);

        final var invoice1 = invoices.get(0);
        assertThat(invoice1.getTotal()).isEqualTo(new BigDecimal("100.00"));
        assertThat(invoice1.getInstallments()).isNotEmpty().hasSize(2);
        final var invoice2 = invoices.get(1);
        assertThat(invoice2.getTotal()).isEqualTo(new BigDecimal("100.00"));
        assertThat(invoice2.getInstallments()).isNotEmpty().hasSize(2);
        final var invoice3 = invoices.get(2);
        assertThat(invoice3.getTotal()).isEqualTo(new BigDecimal("50.00"));
        assertThat(invoice3.getInstallments()).isNotEmpty().hasSize(1);
    }

    @Test
    void shouldThrowExceptionWhenOcurrenceDateIsFuture() {
        when(transactionGateway.findById("transactionId")).thenReturn(createTransaction(LocalDateTime.now().plusDays(1)));
        final var input = new ProcessInstallmentInvoiceInput("transactionId");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> processInstallmentInInvoiceService.processInvoice(input))
                .withMessage("'ocurredOn' must not be in the future");
    }

    private static CardCreditPaymentTransaction createTransaction(LocalDateTime ocurredOn) {
        return CardCreditPaymentTransaction.builder()
                .planId("planId")
                .moment(ocurredOn)
                .debit(new Debit("cId", null)).build();
    }
}