package com.rayllanderson.raybank.models.transaction;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CardTransaction extends Transaction {

    @ManyToOne
    private User establishment;
    @ManyToOne
    private CreditCard payerCard;
    private PaymentType paymentType;
    private Integer installments;

    public static CardTransaction from(PaymentCardInput paymentCardInput, CreditCard payerCard) {
        return CardTransaction.builder()
                .amount(paymentCardInput.getAmount())
                .moment(paymentCardInput.getOcurredOn())
                .description(paymentCardInput.getDescription())
                .establishment(User.fromId(paymentCardInput.getEstablishmentId()))
                .payerCard(payerCard)
                .paymentType(PaymentType.valueOf(paymentCardInput.getPaymentType().name()))
                .installments(paymentCardInput.getInstallments())
                .build();
    }

    public boolean isCreditTransaction() {
        return this.getPaymentType().equals(PaymentType.CREDIT);
    }
}
