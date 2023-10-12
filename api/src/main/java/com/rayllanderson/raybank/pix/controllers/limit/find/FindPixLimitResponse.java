package com.rayllanderson.raybank.pix.controllers.limit.find;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class FindPixLimitResponse {
    private BigDecimal limit;
}
