package com.rayllanderson.raybank.external.payment.models;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @Column(nullable = false)
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
