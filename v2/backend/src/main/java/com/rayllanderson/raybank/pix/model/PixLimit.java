package com.rayllanderson.raybank.pix.model;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PixLimit {

    @Id
    private String id;

    @Column(name = "_limit")
    private BigDecimal limit;

    @OneToOne
    private BankAccount bankAccount;

    @Transient
    public static final BigDecimal DEFAULT_LIMIT = new BigDecimal(5000);

    public static PixLimit defaultLimit(String accountId) {
        return new PixLimit(UUID.randomUUID().toString(), DEFAULT_LIMIT, BankAccount.withId(accountId));
    }

    public void updateLimit(BigDecimal newLimit) {
        this.limit = newLimit;
    }

    public boolean hasLimitFor(BigDecimal amount) {
        return this.limit.compareTo(amount) >= 0;
    }
}
