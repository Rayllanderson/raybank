package com.rayllanderson.raybank.invoice.models;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.installment.models.Installment;
import com.rayllanderson.raybank.invoice.events.InvoiceClosedEvent;
import com.rayllanderson.raybank.invoice.models.inputs.ProcessInvoiceCredit;
import com.rayllanderson.raybank.utils.MathUtils;
import com.rayllanderson.raybank.utils.MoneyUtils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.rayllanderson.raybank.utils.DateManagerUtil.getNextWorkingDayOf;
import static com.rayllanderson.raybank.utils.DateManagerUtil.isAfterOrEquals;
import static com.rayllanderson.raybank.utils.DateManagerUtil.isBeforeOrEquals;

@Getter
@Entity
@NoArgsConstructor
public class Invoice extends AbstractAggregateRoot<Invoice> implements Comparable<Invoice> {
    @Id
    private String id;
    private LocalDate dueDate;
    private LocalDate originalDueDate;
    private LocalDate closingDate;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
    @ManyToOne
    private Card card;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "invoice")
    private List<Installment> installments;
    @OneToMany(cascade =  {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "invoice")
    private List<InvoiceCredit> credits;
    @Transient
    private BigDecimal total;

    public Invoice(String id,
                   LocalDate dueDate,
                   LocalDate originalDueDate,
                   LocalDate closingDate,
                   InvoiceStatus status,
                   Card card,
                   Collection<Installment> installments,
                   Collection<InvoiceCredit> invoiceCredits) {
        this.id = id;
        this.dueDate = dueDate;
        this.originalDueDate = originalDueDate;
        this.closingDate = closingDate;
        this.status = status;
        this.card = card;
        this.installments = new ArrayList<>(installments == null ? new ArrayList<>() : installments);
        this.credits = new ArrayList<>(invoiceCredits == null ? new ArrayList<>() : invoiceCredits);
    }

    @Transient
    public static final int DAYS_BEFORE_CLOSE = 7;

    public static Invoice withId(final String invoiceId) {
        final var i = new Invoice();
        i.id = invoiceId;
        return i;
    }

    public void addInstallment(final Installment installment) {
        if (!canAddInstallment())
            throw new UnprocessableEntityException("Fatura atual não está aberta");
        this.installments.add(installment);
    }

    private boolean canAddInstallment() {
        return isOpen() || status.equals(InvoiceStatus.NONE);
    }

    public boolean hasRemainingBalance() {
        return getTotal().compareTo(BigDecimal.ZERO) < 0;
    }

    public static Invoice createOpenInvoice(LocalDate dueDate, String cardId) {
        final var invoice = create(dueDate, cardId);
        invoice.status = InvoiceStatus.OPEN;
        return invoice;
    }

    public static Invoice create(LocalDate dueDate, String cardId) {
        final LocalDate nextWorkingDay = getNextWorkingDayOf(dueDate);
        return new Invoice(UUID.randomUUID().toString(),
                nextWorkingDay,
                dueDate,
                dueDate.minusDays(DAYS_BEFORE_CLOSE),
                InvoiceStatus.NONE,
                Card.withId(cardId),
                new ArrayList<>(),
                new ArrayList<>());
    }

    public BigDecimal getTotal() {
        final BigDecimal credit = MathUtils.sum(this.getCredits().stream().map(InvoiceCredit::getAmount));
        final BigDecimal debit = MathUtils.sum(this.getDebitInstallments().stream().map(Installment::getValue));
        return MoneyUtils.from(debit.subtract(credit));
    }

    protected List<Installment> getDebitInstallments() {
        return this.getInstallments().stream()
                .filter(Installment::shouldCountAsDebit)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public int compareTo(Invoice o) {
        if (this.originalDueDate.isBefore(o.originalDueDate)) return -1;
        if (this.originalDueDate.isAfter(o.originalDueDate)) return 1;
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

    public boolean hasValueToPay() {
        return this.getTotal().compareTo(BigDecimal.ZERO) > 0;
    }

    public void processCredit(final ProcessInvoiceCredit credit) {
        if (InvoiceCreditType.INVOICE_PAYMENT.equals(credit.getType())) {
            pay(credit.getAmount());
        }
        this.getCredits().add(InvoiceCredit.from(credit, this));
    }

    private void pay(final BigDecimal amount) {
        if (isPaid())
            throw new UnprocessableEntityException("Não é possível receber pagamento para fatura já paga.");

        if (cannotReceivePayment())
            throw new UnprocessableEntityException("Não é possível receber pagamento para essa fatura.");

        this.total = getTotal();

        if (isPaymentDate() || isOverdue()) {
            if (isPartialPayment(amount)) {
                throw new UnprocessableEntityException("Não é possível receber pagamento parcial para fatura fechada ou vencida. Total da fatura: " + this.total);
            }
            this.status = InvoiceStatus.PAID;
        }

        processInstallmentsPayment(amount);
    }

    private void processInstallmentsPayment(final BigDecimal amount) {
        if (installments.isEmpty())
            throw new UnprocessableEntityException("Fatura não pode receber pagamentos sem parcelas.");

        var openInstallments = getOpenInstallments();

        var toPay = MathUtils.divide(amount, openInstallments.size());
        var possiblyAmountRemaing = BigDecimal.ZERO;

        for (Installment installment : openInstallments) {
            possiblyAmountRemaing = possiblyAmountRemaing.add(installment.pay(toPay));
        }
        if (possiblyAmountRemaing.abs().compareTo(BigDecimal.ZERO) > 0)
            processInstallmentsPayment(possiblyAmountRemaing.abs());
    }

    private Collection<Installment> getOpenInstallments() {
        return this.installments.stream().filter(Installment::isOpen).collect(Collectors.toList());
    }

    public boolean canReceivePayment() {
        return isOpen() || isOverdue() || isClosed();
    }

    public boolean cannotReceivePayment() {
        return !canReceivePayment();
    }

    public void open() {
        if (now().isBefore(closingDate) && status.equals(InvoiceStatus.NONE))
            this.status = InvoiceStatus.OPEN;
    }

    public void close() {
        if (isAfterOrEquals(now(), closingDate) && isOpen()) {
            this.status = InvoiceStatus.CLOSED;
            registerEvent(new InvoiceClosedEvent(this));
        }
    }

    public void overdue() {
        if (isOverdue() && isClosed() && hasValueToPay())
            this.status = InvoiceStatus.OVERDUE;
    }

    protected boolean isOverdue() {
        return now().isAfter(dueDate) && !isPaid();
    }

    public boolean isPaid() {
        return this.status.equals(InvoiceStatus.PAID);
    }

    public boolean isOpen() {
        // || now().isBefore(closingDate)
        return this.status.equals(InvoiceStatus.OPEN);
    }

    protected boolean isClosed() {
        // || isAfterOrEquals(now(), closingDate)
        return this.status.equals(InvoiceStatus.CLOSED);
    }

    protected boolean isPartialPayment(BigDecimal amount) {
        return !(amount.compareTo(this.total) == 0 || amount.compareTo(this.total) > 0);
    }

    protected boolean isPaymentDate() {
        final var now = now();
        return isAfterOrEquals(now, closingDate) && isBeforeOrEquals(now, dueDate);
    }

    private static LocalDate now() {
        return LocalDate.now();
    }

    @Transient
    public String getCardId() {
        return this.card.getId();
    }

    public void changeClosingDate() {
        if (this.closingDate.isEqual(LocalDate.now())) {
            this.closingDate = closingDate.plusDays(1);
        }
    }
}
