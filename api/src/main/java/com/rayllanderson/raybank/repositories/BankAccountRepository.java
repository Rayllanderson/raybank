package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.BankAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    boolean existsByAccountNumber(Integer accountNumber);

    @EntityGraph(attributePaths = {"transactions"})
    BankAccount findAccountWithTransactionsByUserId(Long accountId);

    @EntityGraph(attributePaths = {"contacts"})
    BankAccount findAccountWithContactsByUserId(Long accountId);

    @EntityGraph(attributePaths = {"transactions", "contacts"})
    BankAccount findAccountWithTransactionsAndContactsByUserId(Long accountId);

}
