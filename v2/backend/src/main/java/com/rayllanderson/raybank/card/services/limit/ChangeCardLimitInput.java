package com.rayllanderson.raybank.card.services.limit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ChangeCardLimitInput {
    private String cardId;
    private BigDecimal newLimit;
}
