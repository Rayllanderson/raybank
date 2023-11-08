package com.rayllanderson.raybank.external.payment.repositories;

import com.rayllanderson.raybank.external.payment.models.ExternalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalTransactionRepository extends JpaRepository<ExternalTransaction, String> {
}
