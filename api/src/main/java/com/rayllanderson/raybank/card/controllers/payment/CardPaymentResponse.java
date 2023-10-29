package com.rayllanderson.raybank.card.controllers.payment;

import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.utils.MoneyUtils;
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
    private final String amount;

    public static CardPaymentResponse fromTransaction(final Transaction transaction) {
        return new CardPaymentResponse(transaction.getId(), transaction.getMoment(), transaction.getMethod().name(), transaction.getAmount().toString());
    }
}
