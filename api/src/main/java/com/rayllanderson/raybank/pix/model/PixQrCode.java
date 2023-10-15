package com.rayllanderson.raybank.pix.model;

import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.util.PixQrCodeGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PixQrCode {

    @Id
    private String code;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PixQrCodeStatus status;

    @ManyToOne
    private PixKey credit;

    @NotNull
    private LocalDateTime expiresIn;

    @Size(max = 140)
    private String description;

    @Transient
    private static final LocalDateTime DEFAULT_EXPIRATION = LocalDateTime.now().plusDays(3);

    public static PixQrCode newQrCode(BigDecimal amount, PixKey credit, String description) {
        final var qrCode = PixQrCodeGenerator.generateQrCode(credit, amount);
        return new PixQrCode(qrCode, amount, PixQrCodeStatus.WAITING_PAYMENT, credit, DEFAULT_EXPIRATION, description);
    }
}
