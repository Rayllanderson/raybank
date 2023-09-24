package com.rayllanderson.raybank.card.transactions.payment;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Debit;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionType;
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
public class CardPaymentTransaction extends Transaction {

    private Integer installments;

    public static CardPaymentTransaction from(PaymentCardInput paymentCardInput, CreditCard card) {
        final var credit = new Credit(paymentCardInput.getEstablishmentId(), Credit.Destination.ESTABLISHMENT_ACCOUNT);
        final var debit = new Debit(card.getId(), Debit.Origin.CARD);

        return CardPaymentTransaction.builder()
                .amount(paymentCardInput.getAmount())
                .moment(paymentCardInput.getOcurredOn())
                .description(paymentCardInput.getDescription())
                .accountId(card.getAccountId())
                .credit(credit)
                .debit(debit)
                .type(paymentCardInput.isCreditPayment() ? TransactionType.CREDIT_CARD_PAYMENT : TransactionType.DEBIT_CARD_PAYMENT)
                .installments(paymentCardInput.getInstallments())
                .build();
    }

    @Transient
    public boolean isCreditTransaction() {
        return TransactionType.CREDIT_CARD_PAYMENT.equals(this.type);
    }
}
