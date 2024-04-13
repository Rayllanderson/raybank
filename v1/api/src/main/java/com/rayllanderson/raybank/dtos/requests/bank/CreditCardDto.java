package com.rayllanderson.raybank.dtos.requests.bank;

import com.rayllanderson.raybank.models.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardDto {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
    private BankAccount account;
}
