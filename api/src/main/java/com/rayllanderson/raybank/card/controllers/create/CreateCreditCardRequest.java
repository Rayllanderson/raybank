package com.rayllanderson.raybank.card.controllers.create;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateCreditCardRequest {
    private BigDecimal limit;
    private Integer dueDay;
}
