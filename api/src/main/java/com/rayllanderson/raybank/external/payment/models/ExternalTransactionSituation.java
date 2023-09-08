package com.rayllanderson.raybank.external.payment.models;

import lombok.Getter;
import lombok.ToString;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

@ToString
@Getter
@Embeddable
public class ExternalTransactionSituation {

    @NotNull
    @Enumerated(EnumType.STRING)
    private ExternalTransactionStatus status;
    private String message;

    public ExternalTransactionSituation() {
    }

    public ExternalTransactionSituation(ExternalTransactionStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
