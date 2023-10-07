package com.rayllanderson.raybank.refund.service.strategies;

import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class RefundCommand {
    private Transaction transaction;
    private BigDecimal amount;
    private String reason;
}
