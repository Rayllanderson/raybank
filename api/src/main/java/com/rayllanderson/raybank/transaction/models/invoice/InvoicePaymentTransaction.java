package com.rayllanderson.raybank.transaction.models.invoice;

import com.rayllanderson.raybank.invoice.services.credit.InvoiceCreditInput;
import com.rayllanderson.raybank.transaction.models.Transaction;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class InvoicePaymentTransaction extends Transaction {

    private String invoiceId;
    @Embedded
    private Payment payment;

    @Getter
    @Setter
    @Embeddable
    public static class Payment {
        private String identifier;
        @Enumerated(EnumType.STRING)
        private PaymentMethodType methodType;

        private static Payment from(final InvoiceCreditInput payment) {
            final var p = new Payment();
            p.identifier = payment.getAccountId();
            p.methodType = PaymentMethodType.BANK_ACCOUNT;
            return p;
        }
    }

    public static InvoicePaymentTransaction from(final InvoiceCreditInput payment) {
        return InvoicePaymentTransaction.builder()
                .moment(LocalDateTime.now())
                .amount(payment.getAmount())
                .invoiceId(payment.getInvoiceId())
                .payment(Payment.from(payment))
                .build();
    }
}
