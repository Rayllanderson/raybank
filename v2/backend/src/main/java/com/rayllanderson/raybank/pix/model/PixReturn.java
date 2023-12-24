package com.rayllanderson.raybank.pix.model;

import com.rayllanderson.raybank.pix.util.E2EIdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PixReturn {

    @Id
    private String id;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
    @Size(max = 140)
    private String message;
    @NotNull
    private LocalDateTime occuredOn;
    @ManyToOne
    private Pix origin;

    public static PixReturn newReturn(BigDecimal amount, String message, Pix pix) {
        return new PixReturn(E2EIdGenerator.generateD2D(LocalDateTime.now()), amount, message, LocalDateTime.now(), pix);
    }
}
