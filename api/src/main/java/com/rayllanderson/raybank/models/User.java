package com.rayllanderson.raybank.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 1, max = 100)
    private String name;
    @Size(min = 3, max = 100)
    private String username;
    @Size(min = 3, max = 100)
    private String password;
    @OneToOne
    private BankAccount bankAccount;
    @OneToMany
    private Set<Pix> pixKeys = new HashSet<>();
    @OneToMany
    private Set<Contact> contacts = new HashSet<>();
}
