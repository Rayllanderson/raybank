package com.rayllanderson.raybank.bankaccount.services.credit;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.shared.dtos.Origin;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.TRANSACTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CreditAccountService {

    private final BankAccountGateway accountGateway;
    private final CreditAccountMapper creditMapper;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction credit(final CreditAccountInput creditInput) {
        final BankAccount bankAccount = accountGateway.findById(creditInput.getAccountId());

        return processCredit(creditInput, bankAccount);
    }

    @Transactional
    public Transaction creditByAccountNumber(final CreditAccountByNumberInput creditByNumberInput) {
        final BankAccount bankAccount = accountGateway.findByNumber(creditByNumberInput.getNumber());

        final CreditAccountInput creditInput = creditMapper.from(creditByNumberInput, bankAccount.getId());

        return processCredit(creditInput, bankAccount);
    }

    private Transaction processCredit(CreditAccountInput creditInput, BankAccount bankAccount) {
        final Transaction originalTransaction = getReferenceTransaction(creditInput.getOrigin());

        bankAccount.credit(creditInput.getAmount());

        final Transaction transaction = Transaction.creditAccount(creditInput, originalTransaction.getId(), originalTransaction.getDescription());
        return transactionRepository.save(transaction);
    }

    private Transaction getReferenceTransaction(final Origin origin) {
        final String referenceTransactionId = origin.getReferenceTransactionId();
        return transactionRepository.findById(referenceTransactionId)
                .orElseThrow(() -> NotFoundException.with(TRANSACTION_NOT_FOUND, String.format("No Reference Transaction with id %s were found", referenceTransactionId)));
    }
}
