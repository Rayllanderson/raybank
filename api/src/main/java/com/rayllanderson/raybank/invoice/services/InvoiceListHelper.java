package com.rayllanderson.raybank.invoice.services;

import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.rayllanderson.raybank.utils.DateManagerUtil.isAfterOrEquals;
import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthKeepingCurrentDayOfMonth;

@Getter
public class InvoiceListHelper {

    private final int dayOfDueDate;
    private final Set<Invoice> invoices;

    public InvoiceListHelper(final int dayOfDueDate, final Set<Invoice> invoices) {
        this.dayOfDueDate = dayOfDueDate;
        this.invoices = new HashSet<>(invoices == null ? new HashSet<>() : invoices);
    }

    protected Optional<Invoice> getCurrentOpenInvoice() {
        return getInvoiceBeforeClosingDateBy(LocalDate.now());
    }

    protected Invoice getCurrentClosedInvoice() {
        return getInvoiceBeforeOrEqualsDueDateBy(LocalDate.now()).orElse(null);
    }

    protected Invoice getCurrentInvoiceToPay() {
        var currentClosed = getCurrentClosedInvoice();
        if (currentClosed != null && !currentClosed.isPaid())
            return currentClosed;
        return getCurrentOpenInvoice().orElse(null);
    }

    protected static void checkOcurredDateItsOnRange(Invoice currentInvoice, LocalDate ocurredOn) {
        if (ocurredOn.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("'ocurredOn' must not be in the future");
        }

        final LocalDate oldClosingDate = currentInvoice.getClosingDate().minusMonths(1);
        if (isAfterOrEquals(oldClosingDate, ocurredOn)) {
            throw new IllegalArgumentException("'ocurredOn' must not be before current invoice closing date");
        }
    }

    protected LocalDate getNextInvoiceDate(Invoice invoiceCopy) {
        final Month month = invoiceCopy.getDueDate().getMonth();
        final var year = invoiceCopy.getDueDate().getYear();
        return plusOneMonthKeepingCurrentDayOfMonth(LocalDate.of(year, month, dayOfDueDate));
    }

    protected Optional<Invoice> getInvoiceBeforeClosingDateBy(final LocalDate date) {
        if (date == null) throw new NullPointerException("'date' must not be null");
        final ArrayList<Invoice> sortedInvoices = getSortedInvoices();
        return sortedInvoices.stream()
                .filter(invoice -> date.isBefore(invoice.getClosingDate()))
                .findFirst();
    }

    protected Optional<Invoice> getInvoiceBeforeOrEqualsDueDateBy(final LocalDate date) {
        if (date == null) throw new NullPointerException("'date' must not be null");
        final ArrayList<Invoice> sortedInvoices = getSortedInvoices();
        return sortedInvoices.stream()
                .filter(invoice -> date.isBefore(invoice.getDueDate()) || date.isEqual(invoice.getDueDate()))
                .findFirst();
    }

    protected ArrayList<Invoice> getSortedInvoices() {
        final var sortedInvoices = new ArrayList<>(invoices);
        Collections.sort(sortedInvoices);
        return sortedInvoices;
    }

    protected Optional<Invoice> getNextOf(final Invoice invoice) {
        return getInvoiceBeforeClosingDateBy(invoice.getDueDate().plusDays(1));
    }

    protected void add(Invoice invoiceCopy) {
        this.invoices.add(invoiceCopy);
    }
}