package com.rayllanderson.raybank.pix.repositories;

import com.rayllanderson.raybank.pix.model.PixQrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PixQrCodeRepository extends JpaRepository<PixQrCode, String> {
    Optional<PixQrCode> findByCode(String code);
}
