package com.rayllanderson.raybank.transaction.models;

import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.services.PayInvoiceInput;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class InvoiceTransaction extends Transaction {

    @ManyToOne
    private Invoice invoice;
    @Embedded
    private Payment payment;

    @Getter
    @Setter
    @Embeddable
    public static class Payment {
        private String identifier;
        @Enumerated(EnumType.STRING)
        private PaymentMethodType methodType;

        private static Payment from(PayInvoiceInput payment) {
            final var p = new Payment();
            p.identifier = payment.getCardId();
            p.methodType = PaymentMethodType.BANK_ACCOUNT;
            return p;
        }
    }

    public static InvoiceTransaction from(PayInvoiceInput payment) {
        return InvoiceTransaction.builder()
                .moment(LocalDateTime.now())
                .amount(payment.getAmount())
                .invoice(Invoice.withId(payment.getInvoiceId()))
                .payment(Payment.from(payment))
                .build();
    }
}
