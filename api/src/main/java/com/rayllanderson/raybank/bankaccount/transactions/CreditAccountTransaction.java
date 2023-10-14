package com.rayllanderson.raybank.bankaccount.transactions;

import com.rayllanderson.raybank.bankaccount.services.credit.CreditAccountInput;
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
public class CreditAccountTransaction extends Transaction {


    public static Transaction from(final CreditAccountInput input, final String referenceId, final String description) {
        final var credit = new Credit(input.getAccountId(), Credit.Destination.ACCOUNT);
        final var debit = new Debit(input.getOrigin().getIdentifier(), Debit.Origin.valueOf(input.getOrigin().getType().name()));

        return CreditAccountTransaction.builder()
                .amount(input.getAmount())
                .method(input.getTransactionMethod())
                .financialMovement(FinancialMovement.CREDIT)
                .moment(LocalDateTime.now())
                .description(description)
                .debit(debit)
                .credit(credit)
                .type(input.getTransactionType())
                .referenceId(referenceId)
                .accountId(input.getAccountId())
                .build();
    }
}
