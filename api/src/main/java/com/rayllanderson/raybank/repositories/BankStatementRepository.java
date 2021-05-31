package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.BankStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankStatementRepository extends JpaRepository<BankStatement, Long> {
}
