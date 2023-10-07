package com.rayllanderson.raybank.installment.models;

import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.utils.InstallmentUtil;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class InstallmentPlan {
    @Id
    private String id;
    private String transactionId;
    private String originalInvoiceId;
    private String establishmentId;
    private Integer installmentCount;
    private BigDecimal installmentValue;
    private BigDecimal total;
    private BigDecimal refunded;
    private String description;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private InstallmentPlanStatus status;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "installmentPlan")
    private Set<Installment> installments;

    public static InstallmentPlan create(String transactionId,
                                         String invoiceId,
                                         String establishmentId,
                                         Integer numberOfInstallments,
                                         BigDecimal total,
                                         String description) {

        final int installmentCount = numberOfInstallments == null || numberOfInstallments == 0 ? 1 : numberOfInstallments;
        return new InstallmentPlan(UUID.randomUUID().toString(),
                transactionId,
                invoiceId,
                establishmentId,
                installmentCount,
                InstallmentUtil.calculateInstallmentValue(total, installmentCount),
                total,
                BigDecimal.ZERO,
                description,
                LocalDateTime.now(),
                InstallmentPlanStatus.OPEN,
                new HashSet<>());
    }

    public void addInstallment(Installment installment) {
        this.installments.add(installment);
    }

    public void fullRefund() {
        if (this.hasRefunded())
            throw new UnprocessableEntityException("Plan is already refunded");

        if (hasPartialRefund())
            throw new UnprocessableEntityException("Plan cannot be fully refunded. There's already a partial refund for this plan");

        refundInstallmentsPaid();
        cancelOpenInstallments();
        cancelOverdueInstallments();
        this.status = InstallmentPlanStatus.REFUNDED;
    }

    private boolean hasPartialRefund() {
        return this.refunded.compareTo(BigDecimal.ZERO) > 0;
    }

    public void partialRefund(final BigDecimal value) {
        final BigDecimal availableToRefund = this.total.subtract(this.refunded);
        if (value.compareTo(availableToRefund) > 0) {
            throw new UnprocessableEntityException("Available balance to refund is less than amount to refund");
        }

        this.refunded = this.refunded.add(value);

        if (refunded.compareTo(total) == 0)
            this.status = InstallmentPlanStatus.REFUNDED;
    }

    public boolean hasRefunded() {
        return InstallmentPlanStatus.REFUNDED.equals(this.status);
    }

    public List<Installment> getInstallmentsPaid() {
        return this.installments.stream()
                .filter(Installment::isPaid)
                .collect(Collectors.toList());
    }

    public List<Installment> getInstallmentsOverdue() {
        return this.installments.stream()
                .filter(Installment::isOverdue)
                .collect(Collectors.toList());
    }

    public List<Installment> getInstallmentsOpen() {
        return this.installments.stream()
                .filter(Installment::isOpen)
                .collect(Collectors.toList());
    }

    protected void refundInstallmentsPaid() {
        getInstallmentsPaid().forEach(Installment::refund);
    }

    protected void cancelOpenInstallments() {
        getInstallmentsOpen().forEach(Installment::cancel);
    }

    protected void cancelOverdueInstallments() {
        getInstallmentsOverdue().forEach(Installment::cancel);
    }

    public void attachOriginalInvoice(Invoice invoice) {
        if (Objects.isNull(this.originalInvoiceId))
            this.originalInvoiceId = invoice.getId();
    }

    public void checkIfInstallmentsHasInvoice() {
        Optional<Installment> any = this.installments.stream().filter(i -> Objects.isNull(i.getInvoice())).findAny();
        any.ifPresent(i -> {
            throw new UnprocessableEntityException(String.format("Installment %s has no invoice", i.getId()));
        });
    }
}
