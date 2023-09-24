package com.rayllanderson.raybank.bankaccount.services.credit;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.bankaccount.transactions.CreditAccountTransaction;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditAccountService {

    private final BankAccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction credit(final CreditAccountInput creditInput) {
        final BankAccount bankAccount = accountRepository.findById(creditInput.getAccountId())
                .orElseThrow(() -> new NotFoundException(String.format("bank account %s was not found", creditInput.getAccountId())));

        final Transaction originalTransaction = getReferenceTransaction(creditInput);

        bankAccount.credit(creditInput.getAmount());

        final Transaction cardCreditTransaction = CreditAccountTransaction.from(creditInput, originalTransaction.getId(), originalTransaction.getDescription());
        return transactionRepository.save(cardCreditTransaction);
    }

    private Transaction getReferenceTransaction(final CreditAccountInput accountInput) {
        final String referenceTransactionId = accountInput.getOrigin().getReferenceTransactionId();
        return transactionRepository.findById(referenceTransactionId)
                .orElseThrow(() -> new NotFoundException(String.format("No Reference Transaction with id %s were found to account %s",
                        referenceTransactionId, accountInput.getAccountId())));
    }
}
