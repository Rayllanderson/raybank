package com.rayllanderson.raybank.invoice.models;

import com.rayllanderson.raybank.utils.MoneyUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

    public Installment(String id, String description, BigDecimal total, BigDecimal value, LocalDateTime ocurrendOn) {
        this.id = id;
        this.description = description;
        this.total = MoneyUtils.from(total);
        this.value = MoneyUtils.from(value);
        this.ocurrendOn = ocurrendOn;
    }

    public static Installment create(String description, BigDecimal total, BigDecimal value, LocalDateTime ocurrendOn) {
        return new Installment(UUID.randomUUID().toString(), description, total, value, ocurrendOn);
    }
}