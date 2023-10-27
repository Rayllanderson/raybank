package com.rayllanderson.raybank.pix.model.qrcode;

import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.util.PixQrCodeGenerator;
import jakarta.persistence.Column;
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
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PixQrCode {

    @Id
    private String id;

    @NotNull
    @Column(unique = true)
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

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresIn);
    }

    public static PixQrCode newQrCode(BigDecimal amount, PixKey credit, String description) {
        final var qrCode = PixQrCodeGenerator.generateQrCode(credit, amount);
        return new PixQrCode(UUID.randomUUID().toString(), qrCode, amount, PixQrCodeStatus.WAITING_PAYMENT, credit, DEFAULT_EXPIRATION, description);
    }

    public void paid() {
        if (!isExpired())
            this.status = PixQrCodeStatus.PAID;
    }

    public void expire() {
        if (isExpired())
            this.status = PixQrCodeStatus.EXPIRED;
    }
}
