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
public class Credit {
    @Column(name = "credit_id")
    private String id;

    @Column(name = "credit_destination")
    @Enumerated(EnumType.STRING)
    private Destination destination;

    public enum Destination {
        INVOICE, CREDIT_CARD, DEBIT_CARD, ACCOUNT, ESTABLISHMENT_ACCOUNT, BOLETO, PIX
    }
}
