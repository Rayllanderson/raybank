package com.rayllanderson.raybank.bankaccount.transactions;

import com.rayllanderson.raybank.bankaccount.services.debit.DebitAccountInput;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Debit;
import com.rayllanderson.raybank.transaction.models.FinancialMovement;
import com.rayllanderson.raybank.transaction.models.Transaction;
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
public class DebitAccountTransaction extends Transaction {

    public static DebitAccountTransaction from(final DebitAccountInput input, String referenceTransactionId) {
        final var credit = new Credit(input.getDestination().getIdentifier(), Credit.Destination.valueOf(input.getDestination().getType().name()));
        final var debit = new Debit(input.getAccountId(), Debit.Origin.ACCOUNT);

        return DebitAccountTransaction.builder()
                .type(input.getTransaction().getTransactionType())
                .method(input.getTransaction().getTransactionMethod())
                .referenceId(referenceTransactionId)
                .financialMovement(FinancialMovement.DEBIT)
                .amount(input.getAmount())
                .moment(LocalDateTime.now())
                .accountId(input.getAccountId())
                .credit(credit)
                .debit(debit)
                .build();
    }
}
