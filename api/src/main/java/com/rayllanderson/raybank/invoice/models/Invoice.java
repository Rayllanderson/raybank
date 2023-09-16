package com.rayllanderson.raybank.card.models;

import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.rayllanderson.raybank.utils.DateManagerUtil.getNextWorkingDayOf;
import static com.rayllanderson.raybank.utils.DateManagerUtil.isAfterOrEquals;
import static com.rayllanderson.raybank.utils.DateManagerUtil.isBeforeOrEquals;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Invoice implements Comparable<Invoice> {
    @Id
    private String id;
    private LocalDate dueDate;
    private LocalDate closingDate;
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
    @OneToMany(cascade =  {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Installment> installments = new ArrayList<>();

    @Transient
    private static final int DAYS_BEFORE_CLOSE = 6;

    protected void processPayment(String description, BigDecimal total, BigDecimal installmentValue, LocalDateTime date) {
        if (!canProcessPayment())
            throw new UnprocessableEntityException("Fatura atual não está aberta");
        this.total = this.total.add(installmentValue);
        final Installment installment = Installment.create(description, total, installmentValue, date);
        this.installments.add(installment);
    }

    private boolean canProcessPayment() {
        return isOpen() || status.equals(InvoiceStatus.NONE);
    }

    protected static Invoice createOpenInvoice(LocalDate dueDate) {
        final var invoice = create(dueDate);
        invoice.status = InvoiceStatus.OPEN;
        return invoice;
    }

    protected static Invoice create(LocalDate dueDate) {
        final LocalDate nextWorkingDay = getNextWorkingDayOf(dueDate);
        return new Invoice(UUID.randomUUID().toString(),
                nextWorkingDay,
                nextWorkingDay.minusDays(DAYS_BEFORE_CLOSE),
                BigDecimal.ZERO,
                InvoiceStatus.NONE,
                new ArrayList<>());
    }

    @Override
    public int compareTo(Invoice o) {
        if (this.dueDate.isBefore(o.getDueDate())) return -1;
        if (this.dueDate.isAfter(o.dueDate)) return 1;
        else return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;

        Invoice invoice = (Invoice) o;

        return getId() != null ? getId().equals(invoice.getId()) : invoice.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    protected boolean hasValueToPay() {
        return this.total.compareTo(BigDecimal.ZERO) > 0;
    }

    protected void receivePayment(final BigDecimal amount) {
        if (!canReceivePayment())
            throw new UnprocessableEntityException("Não é possível receber pagamento para essa fatura.");

        if (isPaid())
            throw new UnprocessableEntityException("Não é possível receber pagamento para fatura já paga.");

        var toPay = amount;
        if (isAmountGreaterThanTotal(amount)) {
            final BigDecimal refund = amount.subtract(getTotal());
            toPay = amount.subtract(refund);
        }

        if (isPaymentDate() || isOverdue()) {
            if (isPartialPayment(toPay)) {
                throw new UnprocessableEntityException("Não é possível receber pagamento parcial para fatura fechada ou vencida. Total da fatura: " + this.total);
            }
            this.status = InvoiceStatus.PAID;
        }

        total = total.subtract(toPay);
    }

    protected boolean canReceivePayment() {
        return isOpen() || isOverdue() || isClosed();
    }

    public void open() {
        if (now().isBefore(closingDate) && status.equals(InvoiceStatus.NONE))
            this.status = InvoiceStatus.OPEN;
    }

    public void close() {
        if (isAfterOrEquals(now(), closingDate) && isOpen())
            this.status = InvoiceStatus.CLOSED;
    }

    public void overdue() {
        if (isOverdue() && isClosed() && hasValueToPay())
            this.status = InvoiceStatus.OVERDUE;
    }

    protected boolean isOverdue() {
        return now().isAfter(dueDate) && !isPaid();
    }

    protected boolean isPaid() {
        return this.status.equals(InvoiceStatus.PAID);
    }

    protected boolean isOpen() {
        return this.status.equals(InvoiceStatus.OPEN) || now().isBefore(closingDate);
    }

    protected boolean isClosed() {
        return this.status.equals(InvoiceStatus.CLOSED) || isAfterOrEquals(now(), closingDate);
    }

    protected boolean isPartialPayment(BigDecimal amount) {
        return amount.compareTo(this.total) != 0;
    }

    protected boolean isPaymentDate() {
        final var now = now();
        return isAfterOrEquals(now, closingDate) && isBeforeOrEquals(now, dueDate);
    }

    private static LocalDate now() {
        return LocalDate.now();
    }

    private boolean isAmountGreaterThanTotal(BigDecimal amount) {
        return amount.compareTo(this.total) > 0;
    }
}
