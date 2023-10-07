package com.rayllanderson.raybank.boleto.models;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.boleto.BoletoUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Boleto {

    @Id
    private String barCode;

    @DecimalMin("0.1")
    @Column(nullable = false, name = "_value")
    private BigDecimal value;

    @Embedded
    @NotNull
    private Beneficiary beneficiary;

    @ManyToOne(optional = false)
    private BankAccount holder;

    @ManyToOne
    private BankAccount payer;

    @Enumerated(EnumType.STRING)
    private BoletoStatus status;

    @NotNull
    @Column(nullable = false)
    private LocalDate createdAt;

    @NotNull
    @Column(nullable = false)
    private LocalDate expirationDate;

    public static Boleto generate(final String barCode, BigDecimal value, Beneficiary beneficiary, BankAccount holder) {
        final var creationDate = LocalDate.now();
        return new Boleto(barCode, value, beneficiary, holder, null, BoletoStatus.WAITING_PAYMENT, creationDate, BoletoUtil.generateExpirationDate(creationDate));
    }

    public boolean isExpired() {
        return BoletoStatus.EXPIRED.equals(this.status);
    }

    public boolean isPaid() {
        return BoletoStatus.PAID.equals(this.status);
    }

    public void expire() {
        var today = LocalDate.now();
        if (today.isAfter(expirationDate)) {
            this.status = BoletoStatus.EXPIRED;
        }
    }

    public void liquidate() { //todo ajustar
        this.status = BoletoStatus.PAID;
    }
}
