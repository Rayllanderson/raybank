package com.rayllanderson.raybank.boleto;

import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.boleto.models.BoletoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BoletoRepository extends JpaRepository<Boleto, String> {
    Optional<Boleto> findByBarCode(String code);
    List<Boleto> findAllByStatus(BoletoStatus status);
    List<Boleto> findAllByExpirationDateAndStatus(final LocalDate expirationDate, final BoletoStatus status);
}
