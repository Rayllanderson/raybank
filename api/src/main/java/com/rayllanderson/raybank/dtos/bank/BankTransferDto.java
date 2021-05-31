package com.rayllanderson.raybank.dtos.bank;

import com.rayllanderson.raybank.models.BankTransfer;
import com.rayllanderson.raybank.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankTransferDto {
    private User from;
    @NotNull
    @NotEmpty
    private String to;
    private BigDecimal amount;
    @Size(max = 20)
    private String message;

    public static BankTransfer toBankTransfer(BankTransferDto dto){
        return new ModelMapper().map(dto, BankTransfer.class);
    }
}
