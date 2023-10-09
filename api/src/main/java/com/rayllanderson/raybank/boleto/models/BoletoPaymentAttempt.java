package com.rayllanderson.raybank.boleto.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BoletoPaymentAttempt {
    @Id
    private String id;
    @OneToOne
    private Boleto boleto;
    private Integer attempts;

    public static final int MAX_ATTEMPTS_ALLOWED = 3;

    public BoletoPaymentAttempt(final Boleto boleto) {
        this.id = UUID.randomUUID().toString();
        this.attempts = 0;
        this.boleto = boleto;
    }

    public void increase() {
        this.attempts++;
    }

    public boolean maxAttemptsExceeded() {
        return this.attempts.compareTo(MAX_ATTEMPTS_ALLOWED) == 0;
    }
}
