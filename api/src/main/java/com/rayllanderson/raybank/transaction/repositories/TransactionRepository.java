package com.rayllanderson.raybank.transaction.repositories;

import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Optional<Transaction> findByCreditId(String creditId);
    Optional<Transaction> findByIdAndAccountId(String id, String accountId);
    Optional<Transaction> findByReferenceIdAndAccountId(String referenceId, String accountId);
    List<Transaction> findAllByAccountId(String accountId);
    List<Transaction> findAllByAccountIdAndTypeIn(String accountId, List<TransactionType> types);
    List<Transaction> findAllByAccountIdAndTypeNotIn(String accountId, List<TransactionType> types);
}
