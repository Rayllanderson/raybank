package com.rayllanderson.raybank.pix.model;

import jakarta.persistence.Entity;
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
    private LocalDateTime expiresIn;
}
