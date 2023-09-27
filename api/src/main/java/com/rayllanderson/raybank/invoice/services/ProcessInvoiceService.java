package com.rayllanderson.raybank.invoice.services;

import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rayllanderson.raybank.utils.DateManagerUtil.isAfterOrEquals;
import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthOf;
import static com.rayllanderson.raybank.utils.InstallmentUtil.calculateInstallmentValue;
import static com.rayllanderson.raybank.utils.InstallmentUtil.createDescription;

@Service
@RequiredArgsConstructor
public class ProcessInvoiceService {

    private final InvoiceGateway invoiceGateway;
    private final InvoiceRepository invoiceRepository;

    public List<Invoice> processInvoice(BigDecimal total, Integer installmentsCount, String paymentDescription, LocalDateTime ocurredOn, String cardId) {
        final int installments = installmentsCount == null || installmentsCount == 0 ? 1 : installmentsCount;
        final Set<Invoice> invoices = new HashSet<>(invoiceRepository.findAllByCard_Id(cardId));

        final Integer dayOfDueDate = getDayOfDueDate(cardId);
        final InvoiceListHelper invoiceList = new InvoiceListHelper(dayOfDueDate, invoices);

        final Invoice currentInvoice = invoiceList.getCurrentOpenInvoice().orElse(Invoice.createOpenInvoice(plusOneMonthOf(dayOfDueDate), cardId));
        checkOcurredDateItsOnRange(currentInvoice, ocurredOn.toLocalDate());

        final var installmentValue = calculateInstallmentValue(total, installments);

        Invoice invoiceCopy = currentInvoice;
        for (int i = 0; i < installments; i++) {
            invoiceCopy.processPayment(createDescription(paymentDescription, i + 1, installments), total, installmentValue, ocurredOn);
            invoiceList.add(invoiceCopy);
            final var nextInvoice = invoiceList.getNextOf(invoiceCopy);
            invoiceCopy = nextInvoice.orElse(Invoice.create(invoiceList.getNextInvoiceDate(invoiceCopy), cardId));
        }

        invoiceRepository.saveAll(invoiceList.getInvoices());

        return Collections.unmodifiableList(invoiceList.getSortedInvoices());
    }

    private Integer getDayOfDueDate(String cardId) {
        return invoiceGateway.getDayOfDueDateByCardId(cardId);
    }

    private static void checkOcurredDateItsOnRange(Invoice currentInvoice, LocalDate ocurredOn) {
        if (ocurredOn.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("'ocurredOn' must not be in the future");
        }

        final LocalDate oldClosingDate = currentInvoice.getClosingDate().minusMonths(1);
        if (isAfterOrEquals(oldClosingDate, ocurredOn)) {
            throw new IllegalArgumentException("'ocurredOn' must not be before 'current invoice' closing date - " + currentInvoice.getClosingDate());
        }
    }
}
