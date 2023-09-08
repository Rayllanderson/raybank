package com.rayllanderson.raybank.external.payment.models;

import lombok.Getter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Getter
@Entity
public class ExternalTransaction {

    @Id
    private final String id = UUID.randomUUID().toString();

    @NotBlank
    @Column(nullable = false)
    private String externalToken;

    @NotBlank
    @Column(nullable = false)
    private String numberIdentifier;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExternalTransactionType paymentType;

    @NotNull
    @DecimalMin(value = "0.1")
    @Column(nullable = false, name = "_value")
    private BigDecimal value;

    @NotNull
    @Column(nullable = false)
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Embedded
    private ExternalTransactionSituation situation;

    @Deprecated(since = "0.0.1")
    public ExternalTransaction() {
    }

    public ExternalTransaction(String numberIdentifier, BigDecimal value, ExternalTransactionType paymentType, String externalToken) {
        this.numberIdentifier = numberIdentifier;
        this.paymentType = paymentType;
        this.value = value;
        this.externalToken = externalToken;
    }

    public void setSituation(ExternalTransactionSituation situation) {
        this.situation = situation;
    }

    public boolean isValidToken(String token) {
        return this.externalToken.equals(token);
    }

    @PrePersist
    private void prePersistSituationIfNull() {
        if (this.situation == null) {
            this.situation = new ExternalTransactionSituation(ExternalTransactionStatus.SUCCESSFULLY, null);
        }
    }
}
