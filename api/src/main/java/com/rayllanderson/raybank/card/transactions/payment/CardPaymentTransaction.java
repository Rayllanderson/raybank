package com.rayllanderson.raybank.transaction.models.card;

import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.CreditDestinationType;
import com.rayllanderson.raybank.transaction.models.Debit;
import com.rayllanderson.raybank.transaction.models.DebitMethodType;
import com.rayllanderson.raybank.transaction.models.PaymentTransaction;
import com.rayllanderson.raybank.transaction.models.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class CardPaymentTransaction extends PaymentTransaction {

    private Integer installments;
    @Enumerated(EnumType.STRING)
    private CardPaymentType paymentType;

    public static CardPaymentTransaction from(PaymentCardInput paymentCardInput, String accountId) {
        final var credit = new Credit(paymentCardInput.getEstablishmentId(), CreditDestinationType.ESTABLISHMENT);
        final var debit = new Debit(accountId, DebitMethodType.CARD);

        return CardPaymentTransaction.builder()
                .amount(paymentCardInput.getAmount())
                .moment(paymentCardInput.getOcurredOn())
                .description(paymentCardInput.getDescription())
                .credit(credit)
                .debit(debit)
                .paymentType(CardPaymentType.valueOf(paymentCardInput.getPaymentType().name()))
                .installments(paymentCardInput.getInstallments())
                .build();
    }

    @Transient
    public boolean isCreditTransaction() {
        return this.getPaymentType().equals(CardPaymentType.CREDIT);
    }
}
