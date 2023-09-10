package com.rayllanderson.raybank.external.card.payment;

import com.rayllanderson.raybank.models.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class CardPaymentResponse {
    private final String id;
    private final Instant moment;
    private final String type;
    private final BigDecimal amount;

    public static CardPaymentResponse fromTransaction(final Transaction transaction) {
        final var amount = transaction.getAmount().abs();
        return new CardPaymentResponse(transaction.getId(), transaction.getMoment(), transaction.getType().name(), amount);
    }
}
