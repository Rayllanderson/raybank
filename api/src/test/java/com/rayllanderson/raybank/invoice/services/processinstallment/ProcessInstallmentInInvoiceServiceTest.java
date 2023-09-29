package com.rayllanderson.raybank.invoice.services.processinstallment;

import com.rayllanderson.raybank.installment.services.CreateInstallmentPlanMapper;
import com.rayllanderson.raybank.installment.services.CreateInstallmentPlanOutput;
import com.rayllanderson.raybank.installment.services.CreateInstallmentPlanService;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.rayllanderson.raybank.invoice.InvoiceUtils.bigDecimalOf;
import static com.rayllanderson.raybank.invoice.InvoiceUtils.create;
import static com.rayllanderson.raybank.invoice.InvoiceUtils.installment;
import static com.rayllanderson.raybank.invoice.InvoiceUtils.parse;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.NONE;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.OPEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessInvoiceServiceTest {

    @Mock
    private InvoiceGateway invoiceGateway;
    @Mock
    private CreateInstallmentPlanService createInstallmentPlanService;
    @Spy
    private CreateInstallmentPlanMapper planMapper = Mappers.getMapper(CreateInstallmentPlanMapper.class);
    @InjectMocks
    private ProcessInvoiceService processInvoiceService;

    @Test
    void shouldProcessInvoices() {
        when(invoiceGateway.getDayOfDueDateByCardId("cId")).thenReturn(1);
        when(invoiceGateway.findAllByCardIdAndStatus(anyString(), any())).thenReturn(Collections.emptyList());
        final var expectedOutput = new CreateInstallmentPlanOutput("planId", bigDecimalOf(150), bigDecimalOf(50),
                List.of(new CreateInstallmentPlanOutput.InstallmentOutput("i1", parse("2023-09-26")),
                        new CreateInstallmentPlanOutput.InstallmentOutput("i2", parse("2023-10-26")),
                        new CreateInstallmentPlanOutput.InstallmentOutput("i3", parse("2023-11-26"))));
        when(createInstallmentPlanService.create(any())).thenReturn(expectedOutput);
        final var input = new ProcessInvoiceInput(
                "transactionId",
                "cId",
                "establishmentId",
                3,
                BigDecimal.valueOf(150),
                "alibaba",
                LocalDateTime.parse("2023-09-01T18:40:00"));

        final var invoices = processInvoiceService.processInvoice(input);

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
        LocalDate dueDate = LocalDate.now().plusDays(10);
        LocalDate now = LocalDate.now();
        final var savedInvoices = List.of(
                create(dueDate, bigDecimalOf(50.00), OPEN, installment("alibaba 1/3", bigDecimalOf(50.00), now)),
                create(dueDate.plusMonths(1), bigDecimalOf(50.00), NONE, installment("alibaba 2/3", bigDecimalOf(50.00), now.plusMonths(1))),
                create(dueDate.plusMonths(2), bigDecimalOf(50.00), NONE, installment("alibaba 3/3", bigDecimalOf(50.00), now.plusMonths(2)))
        );
        when(invoiceGateway.getDayOfDueDateByCardId("cId")).thenReturn(1);
        when(invoiceGateway.getDayOfDueDateByCardId("cId")).thenReturn(1);
        when(invoiceGateway.findAllByCardIdAndStatus(any(), any())).thenReturn(savedInvoices);
        final var expectedOutput = new CreateInstallmentPlanOutput("planId", bigDecimalOf(150), bigDecimalOf(50),
                List.of(new CreateInstallmentPlanOutput.InstallmentOutput("i1", LocalDate.now()),
                        new CreateInstallmentPlanOutput.InstallmentOutput("i2", LocalDate.now().plusMonths(1))));
        when(createInstallmentPlanService.create(any())).thenReturn(expectedOutput);
        final var input = new ProcessInvoiceInput(
                "transactionId",
                "cId",
                "establishmentId",
                2,
                BigDecimal.valueOf(100),
                "amazon",
                LocalDateTime.now());

        final var invoices = processInvoiceService.processInvoice(input);

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
        final var input = new ProcessInvoiceInput(
                "transactionId",
                "cId",
                "establishmentId",
                2,
                BigDecimal.valueOf(100),
                "amazon",
                LocalDateTime.parse("2099-02-01T20:41:37"));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> processInvoiceService.processInvoice(input))
                .withMessage("'ocurredOn' must not be in the future");
    }
}