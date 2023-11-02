package com.rayllanderson.raybank.transaction.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Debit {
    @Column(name = "debit_id")
    private String id;

    @Column(name = "debit_origin")
    @Enumerated(EnumType.STRING)
    private Origin origin;

    public enum Origin {
        INVOICE, CREDIT_CARD, DEBIT_CARD, ACCOUNT, BOLETO, PIX, ESTABLISHMENT_ACCOUNT
    }
}
