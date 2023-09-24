package com.rayllanderson.raybank.invoice.services;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
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
import java.util.Optional;

import static com.rayllanderson.raybank.invoice.InvoiceUtils.bigDecimalOf;
import static com.rayllanderson.raybank.invoice.InvoiceUtils.create;
import static com.rayllanderson.raybank.invoice.InvoiceUtils.installment;
import static com.rayllanderson.raybank.invoice.InvoiceUtils.parse;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.NONE;
import static com.rayllanderson.raybank.invoice.models.InvoiceStatus.OPEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessInvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private ProcessInvoiceService processInvoiceService;


    @Test
    void shouldProcessInvoices() {
        when(cardRepository.findById("cId")).thenReturn(Optional.of(Card.builder().dayOfDueDate(1).build()));
        when(invoiceRepository.findAllByCard_Id("cId")).thenReturn(Collections.emptyList());

        final var invoices = processInvoiceService.processInvoice(BigDecimal.valueOf(150),
                3,
                "alibaba",
                LocalDateTime.parse("2023-09-01T18:40:00"),
                "cId");

        final var invoice1 = invoices.get(0);
        assertThat(invoice1.getClosingDate()).isEqualTo(LocalDate.parse("2023-09-26"));
        assertThat(invoice1.getDueDate()).isEqualTo(LocalDate.parse("2023-10-02"));
        assertThat(invoice1.getTotal()).isEqualTo(new BigDecimal("50.00"));
        assertThat(invoice1.getInstallments()).isNotEmpty().hasSize(1);
        assertThat(invoice1.getInstallments().get(0).getDescription()).isEqualTo("alibaba 1/3");
        final var invoice2 = invoices.get(1);
        assertThat(invoice2.getClosingDate()).isEqualTo(LocalDate.parse("2023-10-26"));
        assertThat(invoice2.getDueDate()).isEqualTo(LocalDate.parse("2023-11-01"));
        assertThat(invoice2.getTotal()).isEqualTo(new BigDecimal("50.00"));
        assertThat(invoice2.getInstallments()).isNotEmpty().hasSize(1);
        assertThat(invoice2.getInstallments().get(0).getDescription()).isEqualTo("alibaba 2/3");
        final var invoice3 = invoices.get(2);
        assertThat(invoice3.getClosingDate()).isEqualTo(LocalDate.parse("2023-11-25"));
        assertThat(invoice3.getDueDate()).isEqualTo(LocalDate.parse("2023-12-01"));
        assertThat(invoice3.getTotal()).isEqualTo(new BigDecimal("50.00"));
        assertThat(invoice3.getInstallments()).isNotEmpty().hasSize(1);
        assertThat(invoice3.getInstallments().get(0).getDescription()).isEqualTo("alibaba 3/3");
    }

    @Test
    void shouldReuseExistingInvoices() {

        final var savedInvoices = List.of(
                create(parse("2023-10-02"), bigDecimalOf(50.00), OPEN, installment("alibaba 1/3", bigDecimalOf(150.00), bigDecimalOf(50.00))),
                create(parse("2023-11-01"), bigDecimalOf(50.00), NONE, installment("alibaba 2/3", bigDecimalOf(150.00), bigDecimalOf(50.00))),
                create(parse("2023-12-01"), bigDecimalOf(50.00), NONE, installment("alibaba 3/3", bigDecimalOf(150.00), bigDecimalOf(50.00)))
        );
        when(cardRepository.findById("cId")).thenReturn(Optional.of(Card.builder().dayOfDueDate(1).build()));
        when(invoiceRepository.findAllByCard_Id("cId")).thenReturn(savedInvoices);

        final var invoices = processInvoiceService.processInvoice(BigDecimal.valueOf(100), 2, "amazon", LocalDateTime.parse("2023-09-01T20:41:37"), "cId");

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
        when(cardRepository.findById("cId")).thenReturn(Optional.of(Card.builder().dayOfDueDate(1).build()));
        when(invoiceRepository.findAllByCard_Id("cId")).thenReturn(Collections.emptyList());

        assertThatIllegalArgumentException()
                .isThrownBy(() -> processInvoiceService.processInvoice(
                        BigDecimal.valueOf(100),
                        2,
                        "alibaba",
                        LocalDateTime.parse("2023-02-01T20:41:37"),
                        "cId"))
                .withMessage("'ocurredOn' must not be before current invoice closing date");
    }

    @Test
    void shouldThrowExceptionWhenOcurrenceDateIsFuture() {
        when(cardRepository.findById("cId")).thenReturn(Optional.of(Card.builder().dayOfDueDate(1).build()));
        when(invoiceRepository.findAllByCard_Id("cId")).thenReturn(Collections.emptyList());

        assertThatIllegalArgumentException()
                .isThrownBy(() -> processInvoiceService.processInvoice(
                        BigDecimal.valueOf(100),
                        2,
                        "alibaba",
                        LocalDateTime.parse("2099-02-01T20:41:37"),
                        "cId"))
                .withMessage("'ocurredOn' must not be in the future");
    }
}