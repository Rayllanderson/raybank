package com.rayllanderson.raybank.statement.repositories;

import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankStatementRepository extends JpaRepository<BankStatement, String> {
    Optional<BankStatement> findByIdAndAccountId(String id, String accountId);
    List<BankStatement> findAllByAccountId(String accountId);
    List<BankStatement> findAllByAccountIdAndMethodIn(String accountId, List<String> methods);
    List<BankStatement> findAllByAccountIdAndMethodNotIn(String accountId, List<String> methods);
    List<BankStatement> findAllByAccountIdAndCreditDestinationAndType(String accountId, String destination, String type);
}
