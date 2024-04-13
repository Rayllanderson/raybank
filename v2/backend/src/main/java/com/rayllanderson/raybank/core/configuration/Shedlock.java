package com.rayllanderson.raybank.core.configuration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Shedlock {
    @Id
    @Column(length = 64)
    private String name;
    @Column(name = "lock_until", columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime lockUntil;
    @Column(name = "locked_at", columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime lockedAt;
    @Column(name = "locked_by")
    private String lockedBy;
}
