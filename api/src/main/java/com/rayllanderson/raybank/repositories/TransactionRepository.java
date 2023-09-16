package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
