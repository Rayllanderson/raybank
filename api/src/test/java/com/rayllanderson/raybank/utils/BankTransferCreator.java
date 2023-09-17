package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.bankaccount.controllers.requests.BankTransferDto;

import java.math.BigDecimal;

public class BankTransferCreator {

    public static BankTransferDto createBankTransferDto(){
       return BankTransferDto.builder()
                .amount(new BigDecimal(400))
                .message("Hey, take this!")
                .to(UserCreator.createAnotherUserSavedWithAccount().getBankAccount().getAccountNumber().toString())
                .senderId(UserCreator.createUserSavedWithAccount().getId())
                .build();
    }

    public static BankTransferDto createBankTransferDto(BigDecimal toTransfer, String receiver){
        return BankTransferDto.builder()
                .amount(toTransfer)
                .message("Hey, take this!")
                .to(receiver)
                .senderId(UserCreator.createUserSavedWithAccount().getId())
                .build();
    }
}
