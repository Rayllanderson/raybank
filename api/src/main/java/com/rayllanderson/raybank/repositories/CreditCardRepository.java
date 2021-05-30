package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    boolean existsByCardNumber(Long cardNumber);
}
