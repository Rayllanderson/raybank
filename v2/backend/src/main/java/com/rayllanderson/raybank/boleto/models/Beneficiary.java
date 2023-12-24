package com.rayllanderson.raybank.boleto.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Beneficiary {
    @Column(name = "beneficiary_id")
    private String id;

    @Column(name = "beneficiary_type")
    @Enumerated(EnumType.STRING)
    private BeneficiaryType type;

    @Transient
    private Object data;
}
