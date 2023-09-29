package com.rayllanderson.raybank.installment.models;

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
import java.util.Set;
import java.util.UUID;

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
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
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
}
