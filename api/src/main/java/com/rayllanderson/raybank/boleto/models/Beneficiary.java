package com.rayllanderson.raybank.boleto.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Beneficiary {
    private String id;
    @Enumerated(EnumType.STRING)
    private BeneficiaryType type;
}
