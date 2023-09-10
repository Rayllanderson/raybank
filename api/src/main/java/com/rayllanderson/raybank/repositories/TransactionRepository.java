package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.transaction.Transaction;
import com.rayllanderson.raybank.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccountOwnerId(Long ownerId);
    Optional<Transaction> findByIdAndAccountOwnerId(String id, Long ownerId);
    List<Transaction> findAllByAccountOwnerIdAndType(Long ownerId, TransactionType type);
    List<Transaction> findAllByAccountOwnerUserIdAndType(String userId, TransactionType type);
    List<Transaction> findAllByAccountOwnerIdAndTypeNot(Long ownerId, TransactionType type);
}
