package com.rayllanderson.raybank.controllers.creditcard.responses;

import com.rayllanderson.raybank.models.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class PayInvoiceInternalResponse {
    private final String id;
    private final Instant moment;
    private final String type;
    private final BigDecimal amount;

    public static PayInvoiceInternalResponse fromTransaction(final Transaction transaction) {
        final var amount = transaction.getAmount().abs();
        return new PayInvoiceInternalResponse(transaction.getId(), transaction.getMoment(), transaction.getType().name(), amount);
    }
}
