package com.rayllanderson.raybank.boleto.repositories;

import com.rayllanderson.raybank.boleto.models.BoletoPaymentAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoletoPaymentAttemptRepository extends JpaRepository<BoletoPaymentAttempt, String> {
    Optional<BoletoPaymentAttempt> findByBoletoBarCode(final String code);
}
