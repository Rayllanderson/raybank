package com.rayllanderson.raybank.card.transactions.payment;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Debit;
import com.rayllanderson.raybank.transaction.models.FinancialMovement;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
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
public class CardCreditPaymentTransaction extends Transaction {

    private Integer installments;
    private String planId;

    public static CardCreditPaymentTransaction from(final PaymentCardInput paymentCardInput, Card card) {
        final var credit = new Credit(paymentCardInput.getEstablishmentId(), Credit.Destination.ESTABLISHMENT_ACCOUNT);
        final var debit = new Debit(card.getId(), Debit.Origin.CARD);

        return CardCreditPaymentTransaction.builder()
                .amount(paymentCardInput.getAmount())
                .moment(paymentCardInput.getOcurredOn())
                .description(paymentCardInput.getDescription())
                .accountId(card.getAccountId())
                .credit(credit)
                .debit(debit)
                .type(TransactionType.PAYMENT)
                .method(TransactionMethod.CREDIT_CARD)
                .financialMovement(FinancialMovement.DEBIT)
                .installments(paymentCardInput.getInstallments())
                .build();
    }

    public void addPlan(String planId) {
        this.planId = planId;
    }
}
