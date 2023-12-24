package com.rayllanderson.raybank.boleto.controllers.payment;

import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BoletoPaymentResponse {
    private final String transactionId;
    private final BoletoResponse boleto;
    private final LocalDateTime moment;
    private final String type;

    @Getter
    @RequiredArgsConstructor
    private static class BoletoResponse {
        private final String barCode;
        private final BigDecimal amount;
    }

    public static BoletoPaymentResponse fromTransaction(final Transaction transaction) {
        final var boleto = new BoletoResponse(transaction.getCredit().getId(), transaction.getAmount());
        return new BoletoPaymentResponse(transaction.getId(), boleto, transaction.getMoment(), transaction.getType().name());
    }
}
