package com.rayllanderson.raybank.bankaccount.services.debit;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.shared.dtos.Destination;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Transaction;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.DEBIT_SAME_ACCOUNT;

@Service
@RequiredArgsConstructor
public class DebitAccountService {
    private final BankAccountGateway bankAccountGateway;
    private final TransactionGateway transactionGateway;

    @Transactional
    public Transaction debit(final DebitAccountInput debitInput) {
        final BankAccount bankAccount = bankAccountGateway.findById(debitInput.getAccountId());

        validateIfIsSameAccount(bankAccount, debitInput.getDestination());

        final String referenceTransactionId = getReferenceTransactionId(debitInput);

        final var amountToBePaid = debitInput.getAmount();
        bankAccount.debit(amountToBePaid);

        this.bankAccountGateway.save(bankAccount);

        return transactionGateway.save(Transaction.debitAccount(debitInput, referenceTransactionId));
    }

    private void validateIfIsSameAccount(final BankAccount bankAccount, final Destination destination) {
        if (!destination.isAccount()) {
            return;
        }
        if (bankAccount.sameAccount(destination.getIdentifier())) {
            throw UnprocessableEntityException.with(DEBIT_SAME_ACCOUNT, "Não é possível realizar essa operação. Conta a debitar é igual à Conta de destino");
        }
    }

    @Nullable
    private String getReferenceTransactionId(final DebitAccountInput debitInput) {
        final var referenceId = debitInput.getTransaction().getReferenceId();

        if (Objects.isNull(referenceId))
            return null;

        final var referenceTransaction = transactionGateway.findByReferenceIdAndAccountId(referenceId, debitInput.getAccountId());

        return referenceTransaction.map(Transaction::getId).orElse(null);
    }

}
