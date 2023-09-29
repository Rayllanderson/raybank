package com.rayllanderson.raybank.invoice.helper;

import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.rayllanderson.raybank.utils.DateManagerUtil.isAfterOrEquals;
import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthKeepingCurrentDayOfMonth;
import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthOf;

@Getter
public class InvoiceListHelper {

    private final Integer dayOfDueDate;
    private final Set<Invoice> invoices;
    private final String cardId;

    public InvoiceListHelper(final Integer dayOfDueDate, final Collection<Invoice> invoices) {
        this.dayOfDueDate = dayOfDueDate;
        this.invoices = new HashSet<>(invoices == null ? new HashSet<>() : invoices);
        this.cardId = this.invoices.stream().findFirst().map(Invoice::getCardId).orElse(null);
    }

    public InvoiceListHelper(final Collection<Invoice> invoices) {
        this.dayOfDueDate = null;
        this.invoices = new HashSet<>(invoices == null ? new HashSet<>() : invoices);
        this.cardId = this.invoices.stream().findFirst().map(Invoice::getCardId).orElse(null);
    }

    public InvoiceListHelper(final Integer dayOfDueDate, String cardId, final Collection<Invoice> invoices) {
        this.dayOfDueDate = dayOfDueDate;
        this.invoices = new HashSet<>(invoices == null ? new HashSet<>() : invoices);
        this.cardId = cardId;
    }

    public Optional<Invoice> getCurrentOpenInvoice() {
        return getInvoiceBeforeClosingDateBy(LocalDate.now());
    }

    public Invoice getCurrentClosedInvoice() {
        return getInvoiceBeforeOrEqualsDueDateBy(LocalDate.now()).orElse(null);
    }

    public Invoice getCurrentInvoiceToPay() {
        var currentClosed = getCurrentClosedInvoice();
        if (currentClosed != null && !currentClosed.isPaid())
            return currentClosed;
        return getCurrentOpenInvoice().orElse(null);
    }

    public static void checkOcurredDateItsOnRange(Invoice currentInvoice, LocalDate ocurredOn) {
        if (ocurredOn.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("'ocurredOn' must not be in the future");
        }

        final LocalDate oldClosingDate = currentInvoice.getClosingDate().minusMonths(1);
        if (isAfterOrEquals(oldClosingDate, ocurredOn)) {
            throw new IllegalArgumentException("'ocurredOn' must not be before current invoice closing date");
        }
    }

    public LocalDate getNextInvoiceDate(Invoice invoiceCopy) {
        final Month month = invoiceCopy.getDueDate().getMonth();
        final var year = invoiceCopy.getDueDate().getYear();
        return plusOneMonthKeepingCurrentDayOfMonth(LocalDate.of(year, month, dayOfDueDate));
    }

    public Optional<Invoice> getInvoiceBeforeClosingDateBy(final LocalDate date) {
        if (date == null) throw new NullPointerException("'date' must not be null");
        final ArrayList<Invoice> sortedInvoices = getSortedInvoices();
        return sortedInvoices.stream()
                .filter(invoice -> date.isBefore(invoice.getClosingDate()))
                .findFirst();
    }

    public Optional<Invoice> getInvoiceBeforeOrEqualsDueDateBy(final LocalDate date) {
        if (date == null) throw new NullPointerException("'date' must not be null");
        final ArrayList<Invoice> sortedInvoices = getSortedInvoices();
        return sortedInvoices.stream()
                .filter(invoice -> date.isBefore(invoice.getDueDate()) || date.isEqual(invoice.getDueDate()))
                .findFirst();
    }

    public ArrayList<Invoice> getSortedInvoices() {
        final var sortedInvoices = new ArrayList<>(invoices);
        Collections.sort(sortedInvoices);
        return sortedInvoices;
    }

    public Optional<Invoice> getNextOf(final Invoice invoice) {
        return getInvoiceBeforeClosingDateBy(invoice.getDueDate().plusDays(1));
    }

    protected Optional<Invoice> getPreviousOf(final LocalDate dueDate) {
        return getInvoiceBeforeClosingDateBy(dueDate.minusMonths(1).minusDays(1))
                .filter(pv -> !pv.getDueDate().equals(dueDate))
                .stream().findFirst();

    }

    public Optional<Invoice> getPreviousOf(final Invoice invoice) {
        return getPreviousOf(invoice.getDueDate());
    }

    public void add(Invoice invoiceCopy) {
        this.invoices.add(invoiceCopy);
    }

    public Optional<Invoice> getLastInvoice() {
        try {
            return Optional.ofNullable(getSortedInvoices().get(this.invoices.size() - 1));
        } catch (final Exception e) {
            return Optional.empty();
        }
    }

    public LocalDate getLastInvoiceDueDate() {
        return this.getLastInvoice()
                .map(Invoice::getDueDate)
                .orElse(plusOneMonthOf(dayOfDueDate));
    }
}
