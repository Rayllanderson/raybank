package com.rayllanderson.raybank.dtos.requests.bank;

import com.rayllanderson.raybank.models.User;
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
    private User sender;
    @NotNull
    @NotEmpty
    private String to;
    private BigDecimal amount;
    @Size(max = 20)
    private String message;

//    public static BankTransfer toBankTransfer(BankTransferDto dto){
//        return new ModelMapper().map(dto, BankTransfer.class);
//    }
}
