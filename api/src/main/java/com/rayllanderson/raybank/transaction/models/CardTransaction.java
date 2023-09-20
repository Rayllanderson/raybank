package com.rayllanderson.raybank.transaction.models;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.users.model.User;
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
    private CardPaymentType paymentType;
    private Integer installments;

    public static CardTransaction from(PaymentCardInput paymentCardInput, CreditCard payerCard) {
        return CardTransaction.builder()
                .amount(paymentCardInput.getAmount())
                .moment(paymentCardInput.getOcurredOn())
                .description(paymentCardInput.getDescription())
                .establishment(User.fromId(paymentCardInput.getEstablishmentId()))
                .payerCard(payerCard)
                .paymentType(CardPaymentType.valueOf(paymentCardInput.getPaymentType().name()))
                .installments(paymentCardInput.getInstallments())
                .build();
    }

    public boolean isCreditTransaction() {
        return this.getPaymentType().equals(CardPaymentType.CREDIT);
    }

    public String getPayerCardId() {
        return this.payerCard.getId();
    }
}
