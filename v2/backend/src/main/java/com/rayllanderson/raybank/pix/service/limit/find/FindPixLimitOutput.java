package com.rayllanderson.raybank.pix.service.limit.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class FindPixLimitOutput {
    private BigDecimal limit;
}
