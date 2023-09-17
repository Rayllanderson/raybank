package com.rayllanderson.raybank.bankaccount.controllers.reponses;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.services.CreditCardDto;
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
    private String id;
    private String userName;
    private Integer accountNumber;
    private BigDecimal balance;
    private CreditCardDto creditCardDto;

    public static BankAccountDto fromBankAccount(BankAccount bankAccount){
        BankAccountDto dto = new ModelMapper().map(bankAccount, BankAccountDto.class);
        dto.setUserName(bankAccount.getUser().getName());
        dto.setCreditCardDto(CreditCardDto.fromCreditCard(bankAccount.getCreditCard()));
        return dto;
    }
}
