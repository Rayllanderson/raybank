package com.rayllanderson.raybank.pix.model;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PixKey {

    @Id
    @Size(min = 5, max = 99)
    @Column(name = "_key")
    private String key;

    @Enumerated(EnumType.STRING)
    private PixKeyType type;

    @ManyToOne
    private BankAccount bankAccount;

    public static PixKey from(String key, PixKeyType type, String accountId) {
        return new PixKey(key, type, BankAccount.withId(accountId));
    }
}
