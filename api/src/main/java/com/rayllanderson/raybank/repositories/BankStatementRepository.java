package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.BankStatement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankStatementRepository extends JpaRepository<BankStatement, Long> {
    List<BankStatement> findAllByAccountOwnerId(Long ownerId);
    Optional<BankStatement> findByIdAndAccountOwnerId(Long id, Long ownerId);
}
