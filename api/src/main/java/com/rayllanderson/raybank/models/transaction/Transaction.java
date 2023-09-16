package com.rayllanderson.raybank.models.transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Transaction {

    @Id
    protected String id;
    @Column(columnDefinition = "TIMESTAMP")
    protected LocalDateTime moment;
    protected BigDecimal amount;
    protected String referenceId;
    protected String description;

    @PrePersist
    public void createId() {
        if (Objects.isNull(this.id)) {
            this.id = UUID.randomUUID().toString();
            this.referenceId = this.id.split("-")[0];
        }
    }
}
