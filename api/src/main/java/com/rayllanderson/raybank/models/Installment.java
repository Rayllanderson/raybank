package com.rayllanderson.raybank.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "installment")
public class Installment {
    @Id
    private String id;
    private String description;
    private BigDecimal value;
    private LocalDateTime ocurrendOn;

    public static Installment create(String description, BigDecimal value, LocalDateTime ocurrendOn) {
        return new Installment(UUID.randomUUID().toString(), description, value, ocurrendOn);
    }
}