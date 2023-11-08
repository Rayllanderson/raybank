package com.rayllanderson.raybank.pix.service.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PixTransferInput {
    private String debitAccountId;
    private String creditKey;
    private BigDecimal amount;
    private String message;
}
