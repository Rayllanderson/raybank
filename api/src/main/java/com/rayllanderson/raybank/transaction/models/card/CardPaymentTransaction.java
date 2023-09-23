package com.rayllanderson.raybank.transaction.models;

import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class TransactionCardPayment extends Transaction {

    private String establishmentId;
    private String payerCardNumber;
    private CardPaymentType paymentType;
    private Integer installments;

    public static TransactionCardPayment from(PaymentCardInput paymentCardInput) {
        return TransactionCardPayment.builder()
                .amount(paymentCardInput.getAmount())
                .moment(paymentCardInput.getOcurredOn())
                .description(paymentCardInput.getDescription())
                .establishmentId(paymentCardInput.getEstablishmentId())
                .payerCardNumber(String.valueOf(paymentCardInput.getCardNumber()))
                .paymentType(CardPaymentType.valueOf(paymentCardInput.getPaymentType().name()))
                .installments(paymentCardInput.getInstallments())
                .build();
    }

    public boolean isCreditTransaction() {
        return this.getPaymentType().equals(CardPaymentType.CREDIT);
    }
}
