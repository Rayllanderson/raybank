package com.rayllanderson.raybank.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "installments")
public class Installment {
    @Id
    private String id;
    private String description;
    private BigDecimal total;
    @Column(name = "_value")
    private BigDecimal value;
    private LocalDateTime ocurrendOn;

    public static Installment create(String description, BigDecimal total, BigDecimal value, LocalDateTime ocurrendOn) {
        return new Installment(UUID.randomUUID().toString(), description, total, value, ocurrendOn);
    }
}