package com.rayllanderson.raybank.invoice.services;

import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import com.rayllanderson.raybank.models.transaction.CardTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.rayllanderson.raybank.utils.DateManagerUtil.isAfterOrEquals;
import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthOf;
import static com.rayllanderson.raybank.utils.InstallmentUtil.calculateInstallmentValue;
import static com.rayllanderson.raybank.utils.InstallmentUtil.createDescription;

@Service
@RequiredArgsConstructor
public class ProcessInvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CreditCardRepository creditCardRepository;

    public List<Invoice> processInvoice(BigDecimal total, int installments, String paymentDescription, LocalDateTime ocurredOn, String cardId) {
        final Set<Invoice> invoices = invoiceRepository.findAllByCreditCardId(cardId);
        final Integer dayOfDueDate = creditCardRepository.findDayOfDueDateById(cardId);
        final InvoiceListHelper invoiceList = new InvoiceListHelper(dayOfDueDate, invoices);

        final Invoice currentInvoice = invoiceList.getCurrentOpenInvoice().orElse(Invoice.createOpenInvoice(plusOneMonthOf(dayOfDueDate), cardId));
        checkOcurredDateItsOnRange(currentInvoice, ocurredOn.toLocalDate());

        final var installmentValue = calculateInstallmentValue(total, installments);
        currentInvoice.processPayment(createDescription(paymentDescription, 1, installments), total, installmentValue, ocurredOn);

        invoiceRepository.save(currentInvoice);
        invoiceList.add(currentInvoice);

        Invoice invoiceCopy = currentInvoice;

        for (int i = 1; i < installments; i++) {
            final var nextInvoice = invoiceList.getNextOf(invoiceCopy);
            invoiceCopy = nextInvoice.orElse(Invoice.create(invoiceList.getNextInvoiceDate(invoiceCopy), cardId));
            invoiceCopy.processPayment(createDescription(paymentDescription, i + 1, installments), total, installmentValue, ocurredOn);
            invoiceRepository.save(invoiceCopy);
            invoiceList.add(invoiceCopy);
        }
        return Collections.unmodifiableList(invoiceList.getSortedInvoices());
    }

    public void processInvoice(CardTransaction transaction) {
        processInvoice(transaction.getAmount(), transaction.getInstallments(), transaction.getDescription(), transaction.getMoment(), transaction.getPayerCardId());
    }

    private static void checkOcurredDateItsOnRange(Invoice currentInvoice, LocalDate ocurredOn) {
        if (ocurredOn.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("'ocurredOn' must not be in the future");
        }

        final LocalDate oldClosingDate = currentInvoice.getClosingDate().minusMonths(1);
        if (isAfterOrEquals(oldClosingDate, ocurredOn)) {
            throw new IllegalArgumentException("'ocurredOn' must not be before current invoice closing date");
        }
    }
}
