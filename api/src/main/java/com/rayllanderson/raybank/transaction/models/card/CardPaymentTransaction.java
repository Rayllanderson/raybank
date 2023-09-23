package com.rayllanderson.raybank.transaction.models.card;

import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.transaction.models.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CardPaymentTransaction extends Transaction {

    private String establishmentId;
    private String payerCardNumber;
    private CardPaymentType paymentType;
    private Integer installments;

    public static CardPaymentTransaction from(PaymentCardInput paymentCardInput) {
        return CardPaymentTransaction.builder()
                .amount(paymentCardInput.getAmount())
                .moment(paymentCardInput.getOcurredOn())
                .description(paymentCardInput.getDescription())
                .establishmentId(paymentCardInput.getEstablishmentId())
                .payerCardNumber(String.valueOf(paymentCardInput.getCardNumber()))
                .paymentType(CardPaymentType.valueOf(paymentCardInput.getPaymentType().name()))
                .installments(paymentCardInput.getInstallments())
                .build();
    }

    @Transient
    public boolean isCreditTransaction() {
        return this.getPaymentType().equals(CardPaymentType.CREDIT);
    }
}
