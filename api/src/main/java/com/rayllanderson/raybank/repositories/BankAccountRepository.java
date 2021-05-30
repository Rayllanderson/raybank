package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
