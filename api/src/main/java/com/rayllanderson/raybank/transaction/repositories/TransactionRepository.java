package com.rayllanderson.raybank.transaction.repositories;

import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByAccountId(String accountId);
    List<Transaction> findAllByAccountIdAndTypeIn(String accountId, List<TransactionType> types);
    List<Transaction> findAllByAccountIdAndTypeNotIn(String accountId, List<TransactionType> types);
}
