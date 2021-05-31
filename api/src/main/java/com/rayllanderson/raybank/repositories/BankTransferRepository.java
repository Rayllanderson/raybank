package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.BankTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransferRepository extends JpaRepository<BankTransfer, Long> {
}
