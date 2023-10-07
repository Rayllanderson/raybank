package com.rayllanderson.raybank.boleto;

import com.rayllanderson.raybank.boleto.models.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoletoRepository extends JpaRepository<Boleto, String> {
    Optional<Boleto> findByBarCode(String code);
}
