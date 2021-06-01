package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    boolean existsByAccountNumber(Integer accountNumber);
}
