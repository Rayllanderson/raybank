package com.rayllanderson.raybank.installment.models;

import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.utils.InstallmentUtil;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
    private String description;
    private LocalDateTime createdAt;
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
                description,
                LocalDateTime.now(),
                new HashSet<>());
    }

    public void addInstallment(Installment installment) {
        this.installments.add(installment);
    }

    public void fullRefund() {
        refundInstallmentsPaid();
        cancelOpenInstallments();
        cancelOverdueInstallments();
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
