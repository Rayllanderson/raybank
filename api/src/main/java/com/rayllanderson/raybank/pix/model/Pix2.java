package com.rayllanderson.raybank.pix.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pix2 {

    @Id
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PixStatus status;

    @Enumerated(EnumType.STRING)
    private PixType type;

    @ManyToOne
    private PixKey debit;

    @ManyToOne
    private PixKey credit;

    public String getCreditName() {
        return this.credit.getBankAccount().getUser().getName();
    }
}
