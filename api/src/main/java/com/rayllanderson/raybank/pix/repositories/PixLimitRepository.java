package com.rayllanderson.raybank.pix.repositories;

import com.rayllanderson.raybank.pix.model.PixLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PixLimitRepository extends JpaRepository<PixLimit, String> {
    boolean existsByBankAccount_Id(String id);
    Optional<PixLimit> findByBankAccountId(String accountId);

}
