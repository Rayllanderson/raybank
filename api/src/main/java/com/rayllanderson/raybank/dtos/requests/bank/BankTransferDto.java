package com.rayllanderson.raybank.dtos.requests.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankTransferDto {
    private Long senderId;
    @NotNull
    @NotEmpty
    private String to;
    private BigDecimal amount;
    @Size(max = 20)
    private String message;

}
