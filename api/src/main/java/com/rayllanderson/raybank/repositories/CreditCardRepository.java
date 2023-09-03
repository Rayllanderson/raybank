package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
    boolean existsByNumber(Long number);
    boolean existsByBankAccountId(Long accountId);
    Optional<CreditCard> findByBankAccountId(Long accountId);
    Optional<CreditCard> findByNumber(Long number);
}
