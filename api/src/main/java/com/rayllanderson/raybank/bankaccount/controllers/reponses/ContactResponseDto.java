package com.rayllanderson.raybank.bankaccount.controllers.reponses;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponseDto {

    private String id;
    private String username;
    private String name;
    private Integer accountNumber;

    public static ContactResponseDto fromBankAccount(BankAccount bankAccount){
        var user = bankAccount.getUser();
        return ContactResponseDto.builder()
                .accountNumber(bankAccount.getNumber())
                .id(bankAccount.getId())
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }
}
