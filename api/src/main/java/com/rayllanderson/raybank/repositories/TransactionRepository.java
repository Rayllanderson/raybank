package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.Transaction;
import com.rayllanderson.raybank.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankStatementRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccountOwnerId(Long ownerId);
    Optional<Transaction> findByIdAndAccountOwnerId(Long id, Long ownerId);
    List<Transaction> findAllByAccountOwnerIdAndStatementType(Long ownerId, TransactionType type);
    List<Transaction> findAllByAccountOwnerIdAndStatementTypeNot(Long ownerId, TransactionType type);
}
