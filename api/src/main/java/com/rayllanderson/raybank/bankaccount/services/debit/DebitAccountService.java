package com.rayllanderson.raybank.bankaccount.services.debit;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.bankaccount.transactions.DebitAccountTransaction;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.shared.dtos.Destination;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Transaction;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DebitAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final TransactionGateway transactionGateway;

    @Transactional
    public Transaction debit(final DebitAccountInput debitInput) {
        final BankAccount bankAccount = bankAccountRepository.findById(debitInput.getAccountId())
                .orElseThrow(() -> new NotFoundException("Conta bancária não existe"));

        validateIfIsSameAccount(bankAccount, debitInput.getDestination());

        final String referenceTransactionId = getReferenceTransactionId(debitInput);

        var amountToBePaid = debitInput.getAmount();
        bankAccount.debit(amountToBePaid);

        this.bankAccountRepository.save(bankAccount);

        return transactionGateway.save(DebitAccountTransaction.from(debitInput, referenceTransactionId));
    }

    private void validateIfIsSameAccount(final BankAccount bankAccount, final Destination destination) {
        if (!destination.isAccount()) {
            return;
        }
        if (bankAccount.sameAccount(destination.getIdentifier())) {
            throw UnprocessableEntityException.with("Não é possível realizar essa operação. Conta a debitar é igual à Conta de destino");
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
