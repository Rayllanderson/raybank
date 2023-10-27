package com.rayllanderson.raybank.transaction.gateway;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionPostegresGateway implements TransactionGateway {
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction findById(String id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No Transaction with id %s were found", id)));
    }

    @Override
    public List<Transaction> findAllByAccountId(String accountId) {
        return transactionRepository.findAllByAccountId(accountId);
    }

    @Override
    public Transaction findByCreditId(String creditId) {
        return transactionRepository.findByCreditId(creditId)
                .orElseThrow(() -> new NotFoundException(String.format("No Transaction with credit id %s were found", creditId)));
    }

    @Override
    public Transaction save(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> findByReferenceIdAndAccountId(String referenceId, String accountId) {
        return transactionRepository.findByReferenceIdAndAccountId(referenceId, accountId);
    }

    @Override
    public List<Transaction> findAllByAccountIdAndMethodNotIn(String accountId, List<TransactionMethod> methods) {
        return transactionRepository.findAllByAccountIdAndMethodNotIn(accountId, methods);
    }

    @Override
    public List<Transaction> findAllByAccountIdAndMethodIn(String accountId, List<TransactionMethod> methods) {
        return transactionRepository.findAllByAccountIdAndMethodIn(accountId, methods);
    }

    @Override
    public List<Transaction> findAllByAccountIdAndCreditDestinationAndType(String accountId, Credit.Destination destination, TransactionType type) {
        return transactionRepository.findAllByAccountIdAndCreditDestinationAndType(accountId, destination, type);
    }
}
