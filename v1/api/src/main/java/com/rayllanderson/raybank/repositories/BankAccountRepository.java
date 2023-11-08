package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.BankAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    boolean existsByAccountNumber(Integer accountNumber);

    @EntityGraph(attributePaths = {"statements"})
    BankAccount findAccountWithStatementsByUserId(Long accountId);

    @EntityGraph(attributePaths = {"contacts"})
    BankAccount findAccountWithContactsByUserId(Long accountId);

    @EntityGraph(attributePaths = {"statements", "contacts"})
    BankAccount findAccountWithStatementsAndContactsByUserId(Long accountId);

}
