package com.rayllanderson.raybank.transaction.repositories;

import com.rayllanderson.raybank.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
