package com.rayllanderson.raybank.statement.repositories;

import com.rayllanderson.raybank.statement.models.BankStatement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankStatementRepository extends JpaRepository<BankStatement, String> {
    Optional<BankStatement> findByIdAndAccountId(String id, String accountId);

    List<BankStatement> findAllByAccountId(String accountId);
    Page<BankStatement> findAllByAccountId(String accountId, Pageable pageable);

    List<BankStatement> findAllByAccountIdAndMethodIn(String accountId, List<String> methods);
    Page<BankStatement> findAllByAccountIdAndMethodIn(String accountId, List<String> methods, Pageable pageable);

    List<BankStatement> findAllByAccountIdAndMethodNotIn(String accountId, List<String> methods);
    Page<BankStatement> findAllByAccountIdAndMethodNotIn(String accountId, List<String> methods, Pageable pageable);


    List<BankStatement> findAllByAccountIdAndCreditDestinationAndType(String accountId, String destination, String type);
    Page<BankStatement> findAllByAccountIdAndCreditDestinationAndType(String accountId, String destination, String type, Pageable pageable);
}
