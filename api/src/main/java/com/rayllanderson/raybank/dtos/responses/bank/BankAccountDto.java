package com.rayllanderson.raybank.dtos.responses.bank;

import com.rayllanderson.raybank.models.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
    private Long id;
    private String userName;
    private Integer accountNumber;
    private BigDecimal balance;
    private CreditCardDto creditCardDto;

    public static BankAccountDto fromBankAccount(BankAccount bankAccount){
        BankAccountDto dto = new ModelMapper().map(bankAccount, BankAccountDto.class);
        dto.setUserName(bankAccount.getUser().getName());
        return dto;
    }
}
