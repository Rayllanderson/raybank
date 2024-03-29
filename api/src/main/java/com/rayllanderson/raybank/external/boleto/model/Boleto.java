package com.rayllanderson.raybank.external.boleto.model;

import com.rayllanderson.raybank.utils.NumberUtil;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.UUID;

@Getter
@ToString
@Entity
public class Boleto {

    @Id
    private String id;

    @NotBlank
    @Column(nullable = false)
    private String code;

    @DecimalMin("0.1")
    @Column(nullable = false)
    private BigDecimal value;

    @NotBlank
    @Column(nullable = false)
    private String postBackUrl;

    @NotBlank
    @Column(nullable = false)
    private String requester;

    @NotNull
    @Column(nullable = false)
    private final LocalDate creationDate = LocalDate.now();

    @NotNull
    @Column(nullable = false)
    private final LocalDate expirationDate = generateExpirationDate();

    @Enumerated(EnumType.STRING)
    private BoletoStatus status;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    private BoletoHolder holder;

    @Transient
    public static final String PAYMENT_INSTITUTION = "RayBank";

    @Deprecated(since = "0.0.1")
    public Boleto() { }

    public Boleto(BigDecimal value, String postBackUrl, String requester, BoletoHolder holder) {
        this.id = UUID.randomUUID().toString();
        this.value = value;
        this.postBackUrl = postBackUrl;
        this.requester = requester;
        this.status = BoletoStatus.WAITING_PAYMENT;
        this.holder = holder;
        this.code = this.generateCode();
    }

    public static Boleto generate(BigDecimal value, String postBackUrl, String requester, BoletoHolder holder) {
        return new Boleto(value, postBackUrl, requester, holder);
    }

    public boolean isExpired() {
        var today = LocalDate.now();
        return today.isAfter(expirationDate);
    }

    public void expire() {
        if (isExpired()) {
            this.status = BoletoStatus.EXPIRED;
            return;
        }
        throw new IllegalStateException("Não é possível expirar o boleto. Data de vencimento: " + expirationDate);
    }

    public void liquidate() {
        this.status = BoletoStatus.PAID;
    }

    private LocalDate generateExpirationDate() {
        var expiration = creationDate.plusDays(3);
        var dayOfWeek = expiration.getDayOfWeek();
        var expirationIsWeekend = dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);
        if (expirationIsWeekend) {
            return creationDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
        return expiration;
    }

    private String generateCode() {
        var randomNumbers = String.valueOf(NumberUtil.generateRandom(15)) + NumberUtil.generateRandom(18);
        return "1319" + randomNumbers + String.format("%010d", this.value.intValue());
    }
}
