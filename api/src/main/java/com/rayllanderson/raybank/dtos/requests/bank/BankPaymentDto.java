package com.rayllanderson.raybank.dtos.requests.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankPaymentDto {

    private Long ownerId;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

}
