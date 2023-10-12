package com.rayllanderson.raybank.pix.model;

import com.rayllanderson.raybank.pix.model.key.PixKey;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pix {

    @Id
    private String id;

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
