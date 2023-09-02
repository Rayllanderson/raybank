package com.rayllanderson.raybank.models;

import com.rayllanderson.raybank.utils.InstallmentUtil;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.rayllanderson.raybank.utils.DateManagerUtil.isAfterOrEquals;
import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthKeepingCurrentDayOfMonth;
import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthOf;

@Getter
@Entity
public class InvoiceList {
    @Id
    private String id;
    private Integer dayOfdueDate;
    @OneToMany
    private Set<Invoice> invoices;

    public InvoiceList() {
        this.invoices = new HashSet<>();
    }

    public InvoiceList(String id, Integer dayOfdueDate, Set<Invoice> invoices) {
        this.id = id;
        this.dayOfdueDate = dayOfdueDate;
        this.invoices = invoices;
    }

    public static InvoiceList create(Integer dayOfdueDate) {
        final var invoices = new HashSet<Invoice>();
        invoices.add(Invoice.create(plusOneMonthOf(dayOfdueDate)));
        return new InvoiceList(UUID.randomUUID().toString(), dayOfdueDate, invoices);
    }

    public void process(BigDecimal total, int installments, String paymentDescription, LocalDateTime ocurredOn) {
        final Invoice currentInvoice = getCurrentInvoice();
        checkOcurredDateItsOnRange(currentInvoice, ocurredOn.toLocalDate());

        final var installmentValue = InstallmentUtil.calculateInstallmentValue(total, installments);
        currentInvoice.processPayment(InstallmentUtil.createDescription(paymentDescription, 1, installments), installmentValue, ocurredOn);

        Invoice invoiceCopy = currentInvoice;

        for (int i = 1; i < installments; i++) {
            final var nextInvoice = getNextOf(invoiceCopy);
            invoiceCopy = nextInvoice.orElse(Invoice.create(getNextInvoiceDate(invoiceCopy)));
            invoiceCopy.processPayment(InstallmentUtil.createDescription(paymentDescription, i + 1, installments), installmentValue, ocurredOn);
            this.invoices.add(invoiceCopy);
        }
    }

    public Invoice getCurrentInvoice() {
        return getInvoiceBy(LocalDate.now())
                .orElseThrow(() -> new IllegalStateException("No currency invoice was found"));
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

    private LocalDate getNextInvoiceDate(Invoice invoiceCopy) {
        final Month month = invoiceCopy.getDueDate().getMonth();
        final var year = invoiceCopy.getDueDate().getYear();
        return plusOneMonthKeepingCurrentDayOfMonth(LocalDate.of(year, month, dayOfdueDate));
    }

    private Optional<Invoice> getInvoiceBy(final LocalDate date) {
        if (date == null) throw new NullPointerException("'date' must not be null");
        final var sortedInvoices = new ArrayList<>(invoices);
        Collections.sort(sortedInvoices);
        return sortedInvoices.stream()
                .filter(invoice -> date.isBefore(invoice.getClosingDate()))
                .findFirst();
    }

    private Optional<Invoice> getNextOf(final Invoice invoice) {
        return getInvoiceBy(invoice.getDueDate().plusDays(1));
    }

    /**
     * @return unmodifiableSet
     */
    public Set<Invoice> getInvoices() {
        return Collections.unmodifiableSet(invoices);
    }
}
