package com.rayllanderson.raybank.card.models.inputs;

import com.rayllanderson.raybank.shared.dtos.Origin;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreditInput {

    private BigDecimal amount;
    private Origin origin;
}
