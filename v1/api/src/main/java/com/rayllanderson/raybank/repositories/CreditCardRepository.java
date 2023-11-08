package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    boolean existsByCardNumber(Long cardNumber);
    Optional<CreditCard> findByBankAccountId(Long accountId);
    Optional<CreditCard> findByCardNumber(Long cardNumber);
}
