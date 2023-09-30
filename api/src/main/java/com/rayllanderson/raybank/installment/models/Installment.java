package com.rayllanderson.raybank.installment.models;

import com.rayllanderson.raybank.utils.MoneyUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "installments")
public class Installment {
    @Id
    private String id;
    private String description;
    @Column(name = "_value")
    private BigDecimal value;
    private BigDecimal paidValue;
    @Enumerated(EnumType.STRING)
    private InstallmentStatus status;
    private LocalDate dueDate;
    @ManyToOne
    private InstallmentPlan installmentPlan;

    public Installment(String id, String description, BigDecimal value, LocalDate dueDate, InstallmentStatus status, InstallmentPlan installmentPlan) {
        this.id = id;
        this.description = description;
        this.value = MoneyUtils.from(value);
        this.dueDate = dueDate;
        this.installmentPlan = installmentPlan;
        this.status = status;
    }

    public static Installment create(String description, LocalDate dueDate, InstallmentPlan installmentPlan) {
        return new Installment(UUID.randomUUID().toString(), description, installmentPlan.getInstallmentValue(), dueDate, InstallmentStatus.OPEN, installmentPlan);
    }

    public static Installment withId(String id) {
        Installment installment = new Installment();
        installment.id = id;
        return installment;
    }

    public boolean isPaid() {
        return this.status.equals(InstallmentStatus.PAID);
    }

    public boolean isRefunded() {
        return this.status.equals(InstallmentStatus.REFUNDED);
    }

    public boolean isOpen() {
        return this.status.equals(InstallmentStatus.OPEN);
    }

    public boolean isOverdue() {
        return this.status.equals(InstallmentStatus.OVERDUE);
    }

    public void refund() {
        if (isPaid())
            this.status = InstallmentStatus.REFUNDED;
    }

    public void cancel() {
        if (!isPaid() && !isRefunded())
            this.status = InstallmentStatus.CANCELED;
    }
}