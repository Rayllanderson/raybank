package com.rayllanderson.raybank.pix.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PixQrCode {

    @Id
    private String code;

    @Enumerated(EnumType.STRING)
    private PixQrCodeStatus status;

    private LocalDateTime expiresIn;
}
