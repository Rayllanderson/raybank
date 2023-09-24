package com.rayllanderson.raybank.invoice.controllers.payment;

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
public class InvoicePaymentInternalResponse {
    private final String transactionId;
    private final LocalDateTime moment;
    private final BigDecimal amount;
    private final Payment payment;
    private final Destination destination;

    @Getter
    @RequiredArgsConstructor
    private static class Payment {
        private final String type;
        private final String origin;
    }

    @Getter
    @RequiredArgsConstructor
    private static class Destination {
        private final String id;
        private final String type;
    }

    public static InvoicePaymentInternalResponse fromTransaction(final Transaction transaction) {
        final var amount = transaction.getAmount().abs();

        final var payment = new Payment(transaction.getType().name(), transaction.getDebit().getOrigin().name());
        final var destination = new Destination(transaction.getCredit().getId(), transaction.getCredit().getDestination().name());

        return new InvoicePaymentInternalResponse(transaction.getId(), transaction.getMoment(), amount, payment, destination);
    }
}
