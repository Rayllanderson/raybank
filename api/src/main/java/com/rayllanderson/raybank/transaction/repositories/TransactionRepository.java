package com.rayllanderson.raybank.transaction.repositories;

import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Optional<Transaction> findByCreditId(String creditId);
    Optional<Transaction> findByIdAndAccountId(String id, String accountId);
    Optional<Transaction> findByReferenceIdAndAccountId(String referenceId, String accountId);
    List<Transaction> findAllByAccountId(String accountId);
    List<Transaction> findAllByReferenceId(String referenceId);
    List<Transaction> findAllByReferenceIdAndType(String referenceId, TransactionType type);
    List<Transaction> findAllByAccountIdAndMethodIn(String accountId, List<TransactionMethod> methods);
    List<Transaction> findAllByAccountIdAndMethodNotIn(String accountId, List<TransactionMethod> methods);
    List<Transaction> findAllByAccountIdAndCreditDestinationAndType(String accountId, Credit.Destination destination, TransactionType type);
}
