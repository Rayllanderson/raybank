package com.rayllanderson.raybank.pix.repositories;

import com.rayllanderson.raybank.pix.model.PixQrCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixQrCodeRepository extends JpaRepository<PixQrCode, String> {
}
