package com.rayllanderson.raybank.invoice.controllers.payment;

import com.rayllanderson.raybank.transaction.models.invoice.InvoicePaymentTransaction;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class InvoicePaymentInternalResponse {
    private final String id;
    private final LocalDateTime moment;
    private final String paymentType;
    private final BigDecimal amount;

    public static InvoicePaymentInternalResponse fromTransaction(final Transaction transaction) {
        final InvoicePaymentTransaction invoicePaymentTransaction = (InvoicePaymentTransaction) transaction;
        final var amount = transaction.getAmount().abs();
        return new InvoicePaymentInternalResponse(transaction.getId(), transaction.getMoment(), invoicePaymentTransaction.getPayment().getMethodType().name(), amount);
    }
}
