package com.rayllanderson.raybank.card.repository;

import com.rayllanderson.raybank.card.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, String> {
    boolean existsByNumber(Long number);
    boolean existsByBankAccountId(String accountId);
    Optional<Card> findByBankAccountId(String accountId);
    Optional<Card> findByNumber(Long number);
}
