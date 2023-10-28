package com.rayllanderson.raybank.boleto.models;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.boleto.BoletoUtil;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
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

import static com.rayllanderson.raybank.boleto.models.BoletoStatus.EXPIRED;
import static com.rayllanderson.raybank.boleto.models.BoletoStatus.PAID;
import static com.rayllanderson.raybank.boleto.models.BoletoStatus.PROCESSING;
import static com.rayllanderson.raybank.boleto.models.BoletoStatus.PROCESSING_FAILURE;
import static com.rayllanderson.raybank.boleto.models.BoletoStatus.REFUNDED;
import static com.rayllanderson.raybank.boleto.models.BoletoStatus.WAITING_PAYMENT;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.BOLETO_EXPIRED;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.BOLETO_LIQUIDATED;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.BOLETO_NOT_LIQUIDATED;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.BOLETO_PAID;

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
        return new Boleto(barCode, value, beneficiary, holder, null, WAITING_PAYMENT, creationDate, BoletoUtil.generateExpirationDate(creationDate));
    }

    public boolean isExpired() {
        return EXPIRED.equals(this.status);
    }

    public boolean isPaid() {
        return PAID.equals(this.status);
    }

    public void expire() {
        var today = LocalDate.now();
        if (today.isAfter(expirationDate)) {
            this.status = EXPIRED;
        }
    }

    public void liquidate(final String payerId) {
        if (!isExpired() || !isPaid()) {
            this.status = PROCESSING;
            this.payer = BankAccount.withId(payerId);
        }
    }

    public void concludePayment() {
        if (isLiquidated())
            this.status = PAID;
        else
            throw UnprocessableEntityException.with(BOLETO_NOT_LIQUIDATED, "Boleto is not liquidated");
    }

    public boolean isLiquidated() {
        return PROCESSING.equals(this.status);
    }

    public void unprocessed() {
        if (isLiquidated())
            this.status = PROCESSING_FAILURE;
    }

    public void validateIfCanReceivePayment() {
        if (isPaid())
            throw UnprocessableEntityException.with(BOLETO_PAID, "Boleto já pago");
        if (isExpired())
            throw UnprocessableEntityException.with(BOLETO_EXPIRED, "Boleto expirado");
        if (isLiquidated())
            throw UnprocessableEntityException.with(BOLETO_LIQUIDATED, "Boleto está sendo processado");
    }

    public String getPayerId() {
        return this.payer.getId();
    }

    public void refund() {
        if (this.status.equals(PROCESSING_FAILURE)) {
            this.status = REFUNDED;
        }
    }
}
