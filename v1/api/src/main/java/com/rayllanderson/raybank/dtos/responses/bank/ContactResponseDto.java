package com.rayllanderson.raybank.dtos.responses.bank;

import com.rayllanderson.raybank.models.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponseDto {

    private Long id;
    private String username;
    private String name;
    private Integer accountNumber;

    public static ContactResponseDto fromBankAccount(BankAccount bankAccount){
        var user = bankAccount.getUser();
        return ContactResponseDto.builder()
                .accountNumber(bankAccount.getAccountNumber())
                .id(bankAccount.getId())
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }
}
