package com.rayllanderson.raybank.bankaccount.repository;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    boolean existsByAccountNumber(Integer accountNumber);

    @EntityGraph(attributePaths = {"bankStatements"})
    BankAccount findAccountWithBankStatementsByUserId(String userId);

    Optional<BankAccount> findByUserId(String userId);

    @EntityGraph(attributePaths = {"contacts"})
    BankAccount findAccountWithContactsByUserId(String userId);

    @EntityGraph(attributePaths = {"bankStatements", "contacts"})
    BankAccount findAccountWithBankStatementsAndContactsByUserId(String userId);

}
