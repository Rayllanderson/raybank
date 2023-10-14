package com.rayllanderson.raybank.pix.model;

import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.util.E2EIdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pix {

    @Id
    private String id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PixType type;

    @ManyToOne
    private PixKey debit;

    @ManyToOne
    private PixKey credit;

    private String message;

    private LocalDateTime occuredOn;

    public String getCreditName() {
        return this.credit.getBankAccount().getUser().getName();
    }

    public String getCreditAccountId() {
        return this.credit.getBankAccount().getId();
    }

    public String getDebitAccountId() {
        return this.debit.getBankAccount().getId();
    }

    public static Pix newTransfer(PixKey debit, PixKey credit, BigDecimal amount, String message) {
        final LocalDateTime occured = LocalDateTime.now();
        return new Pix(E2EIdGenerator.generateE2E(occured), amount, PixType.TRANSFER, debit, credit, message, occured);
    }
}
