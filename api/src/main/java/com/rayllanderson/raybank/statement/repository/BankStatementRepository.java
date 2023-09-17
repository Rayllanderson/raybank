package com.rayllanderson.raybank.statement.repository;

import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.statement.models.BankStatementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankStatementRepository extends JpaRepository<BankStatement, String> {
    List<BankStatement> findAllByAccountOwnerId(Long ownerId);
    List<BankStatement> findAllByAccountOwnerUserId(String userId);
    Optional<BankStatement> findByIdAndAccountOwnerId(String id, Long ownerId);
    List<BankStatement> findAllByAccountOwnerIdAndType(Long ownerId, BankStatementType type);
    List<BankStatement> findAllByAccountOwnerUserIdAndType(String userId, BankStatementType type);
    List<BankStatement> findAllByAccountOwnerIdAndTypeNot(Long ownerId, BankStatementType type);
    List<BankStatement> findAllByAccountOwnerUserIdAndTypeNot(String userId, BankStatementType type);
}
