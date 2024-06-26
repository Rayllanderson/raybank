package com.rayllanderson.raybank.card.transactions.payment;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.CardPaymentInput;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Debit;
import com.rayllanderson.raybank.transaction.models.FinancialMovement;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import com.rayllanderson.raybank.utils.MoneyUtils;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CardCreditPaymentTransaction extends Transaction {

    private Integer installments;
    private String planId;

    public static CardCreditPaymentTransaction from(final CardPaymentInput cardPaymentInput, Card card) {
        final var credit = new Credit(cardPaymentInput.getEstablishmentId(), Credit.Destination.ESTABLISHMENT_ACCOUNT);
        final var debit = new Debit(card.getId(), Debit.Origin.CREDIT_CARD);

        return CardCreditPaymentTransaction.builder()
                .id(UUID.randomUUID().toString())
                .amount(MoneyUtils.from(cardPaymentInput.getAmount()))
                .moment(cardPaymentInput.getOcurredOn())
                .description(cardPaymentInput.getDescription())
                .accountId(card.getAccountId())
                .credit(credit)
                .debit(debit)
                .type(TransactionType.PAYMENT)
                .method(TransactionMethod.CREDIT_CARD)
                .financialMovement(FinancialMovement.DEBIT)
                .installments(cardPaymentInput.getInstallments())
                .build();
    }

    public void addPlan(String planId) {
        this.planId = planId;
    }
}
