package com.rayllanderson.raybank.pix.repositories;

import com.rayllanderson.raybank.pix.model.key.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PixKeyRepository extends JpaRepository<PixKey, String> {
    boolean existsByKey(String key);
    Integer countByBankAccountId(String accountId);
    List<PixKey> findAllByBankAccountId(String accountId);
}
