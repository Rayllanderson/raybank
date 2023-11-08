package com.rayllanderson.raybank.contact.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ContactAccount {

    @Column(name = "account_id")
    private String id;
    @Column(name = "account_number")
    private String number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactAccount)) return false;

        ContactAccount account = (ContactAccount) o;

        return getId() != null ? getId().equals(account.getId()) : account.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
