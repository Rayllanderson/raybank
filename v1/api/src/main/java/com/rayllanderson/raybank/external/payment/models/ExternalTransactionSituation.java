package com.rayllanderson.raybank.external.payment.models;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

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
