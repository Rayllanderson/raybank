package com.rayllanderson.raybank.card.controllers.external;

import com.rayllanderson.raybank.card.transactions.payment.CardPaymentTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CardPaymentResponse {
    private final String transactionId;
    private final LocalDateTime moment;
    private final String type;
    private final BigDecimal amount;

    public static CardPaymentResponse fromTransaction(final CardPaymentTransaction transaction) {
        return new CardPaymentResponse(transaction.getId(), transaction.getMoment(), transaction.getType().name(), transaction.getAmount());
    }
}
