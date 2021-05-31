package com.rayllanderson.raybank.dtos.responses;

import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
    private Long id;
    private Integer accountNumber;
    private CreditCardDto creditCardDto;

    public static BankAccountDto fromBankAccount(BankAccount bankAccount){
        return new ModelMapper().map(bankAccount, BankAccountDto.class);
    }

}
