package com.rayllanderson.raybank.card.controllers;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardDto {
//TODO::deletar
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
    private BankAccount account;
}
