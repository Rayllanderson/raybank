package com.rayllanderson.raybank.card.transactions.credit;

import com.rayllanderson.raybank.card.services.credit.CardCreditInput;
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

import java.time.LocalDateTime;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CardCreditTransaction extends Transaction {

    public static CardCreditTransaction from(final CardCreditInput input, String accountId, String referenceId) {
        final var credit = new Credit(input.getCardId(), Credit.Destination.CREDIT_CARD);
        final var debit = new Debit(input.getOrigin().getIdentifier(), Debit.Origin.valueOf(input.getOrigin().getType().name()));

        return CardCreditTransaction.builder()
                .amount(input.getAmount())
                .moment(LocalDateTime.now())
                .description(input.getOrigin().getType().name())
                .financialMovement(FinancialMovement.CREDIT)
                .debit(debit)
                .credit(credit)
                .type(input.getTransactionType())
                .method(input.getTransactionMethod())
                .referenceId(referenceId)
                .accountId(accountId)
                .build();
    }
}
