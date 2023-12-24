package com.rayllanderson.raybank.bankaccount.services.transfer;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BankAccountTransferInput {
    private String senderId;
    private int beneficiaryAccountNumber;
    private BigDecimal amount;
    private String description;
}
