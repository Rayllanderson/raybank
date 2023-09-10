package com.rayllanderson.raybank.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    private String id;
    @Size(min = 1, max = 100)
    private String name;
    @Size(min = 3, max = 100)
    private String username;
    private String authorities;
    @Enumerated(EnumType.STRING)
    private UserType type;
    @OneToOne(orphanRemoval = true)
    private BankAccount bankAccount;
    @OneToMany(orphanRemoval = true)
    private Set<Pix> pixKeys = new HashSet<>();

    public User(String id, String name, String username, String authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.authorities = authorities;
    }
}
