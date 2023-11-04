package com.rayllanderson.raybank.transaction.gateway;

import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import io.github.rayexpresslibraries.ddd.domain.pagination.Pagination;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TransactionGateway {
    Transaction findById(final String id);
    List<Transaction> findAllByAccountId(final String accountId);
    Pagination<Transaction> findAllByAccountId(final String accountId, Pageable pageable);
    Transaction findByCreditId(final String creditId);
    Transaction save(Transaction transaction);
    Optional<Transaction> findByReferenceIdAndAccountId(String referenceId, String accountId);
    List<Transaction> findAllByReferenceId(String referenceId);
    List<Transaction> findAllByReferenceIdAndType(String referenceId, TransactionType type);
    List<Transaction> findAllByAccountIdAndMethodNotIn(String accountId, List<TransactionMethod> methods);
    List<Transaction> findAllByAccountIdAndMethodIn(String accountId, List<TransactionMethod> methods);
    List<Transaction> findAllByAccountIdAndCreditDestinationAndType(String accountId, Credit.Destination destination, TransactionType type);
}
