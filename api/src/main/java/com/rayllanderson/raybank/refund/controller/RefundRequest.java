package com.rayllanderson.raybank.refund.controller;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RefundRequest {
    private BigDecimal amount;
    private String reason;
}
