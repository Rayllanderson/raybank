package com.rayllanderson.raybank.transaction.gateway;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import io.github.rayexpresslibraries.ddd.domain.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.TRANSACTION_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class TransactionPostgresGateway implements TransactionGateway {
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction findById(String id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> NotFoundException.withFormatted(TRANSACTION_NOT_FOUND,"No Transaction with id %s were found", id));
    }

    @Override
    public List<Transaction> findAllByAccountId(String accountId) {
        return transactionRepository.findAllByAccountId(accountId);
    }

    @Override
    public Pagination<Transaction> findAllByAccountId(String accountId, Pageable pageable) {
        final var page = transactionRepository.findAllByAccountId(accountId, pageable);
        return new Pagination<>(page.getNumber(), page.getSize(), page.getTotalElements(), page.getContent());
    }

    @Override
    public Transaction findByCreditId(String creditId) {
        return transactionRepository.findByCreditId(creditId)
                .orElseThrow(() -> NotFoundException.withFormatted(TRANSACTION_NOT_FOUND, "No Transaction with credit id %s were found", creditId));
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
    public List<Transaction> findAllByReferenceId(String referenceId) {
        return transactionRepository.findAllByReferenceId(referenceId);
    }

    @Override
    public List<Transaction> findAllByReferenceIdAndType(String referenceId, TransactionType type) {
        return transactionRepository.findAllByReferenceIdAndType(referenceId, type);
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
