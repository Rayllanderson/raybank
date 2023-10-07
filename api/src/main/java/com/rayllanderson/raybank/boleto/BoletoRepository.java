package com.rayllanderson.raybank.boleto;

import com.rayllanderson.raybank.external.boleto.model.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoletoRepository extends JpaRepository<Boleto, String> {
    Optional<Boleto> findByCode(String code);
}
