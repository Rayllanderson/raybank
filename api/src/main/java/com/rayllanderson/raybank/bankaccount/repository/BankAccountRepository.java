package com.rayllanderson.raybank.bankaccount.repository;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    boolean existsByNumber(Integer accountNumber);
    Optional<BankAccount> findByNumber(int number);
}
