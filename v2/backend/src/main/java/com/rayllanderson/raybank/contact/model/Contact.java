package com.rayllanderson.raybank.contact.model;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    private String id;

    private String name;

    @Embedded
    private ContactAccount account;

    @ManyToOne
    private BankAccount owner;

    public static Contact from(BankAccount bankAccount, String onwerId) {
        final var account = new ContactAccount(bankAccount.getId(), String.valueOf(bankAccount.getNumber()));
        return new Contact(UUID.randomUUID().toString(), bankAccount.getUser().getName(), account, BankAccount.withId(onwerId));
    }

    public boolean hasChangedName(String accountName) {
        return !this.name.equals(accountName);
    }

    public void updateName(String accountName) {
        this.name = accountName;
    }
}
