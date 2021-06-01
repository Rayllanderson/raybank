package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;

import java.math.BigDecimal;

public class BankTransferCreator {

    public static BankTransferDto createBankTransferDto(){
       return BankTransferDto.builder()
                .amount(new BigDecimal(400))
                .message("Hey, take this!")
                .to(UserCreator.createAnotherUserSavedWithAccount().getBankAccount().getAccountNumber().toString())
                .sender(UserCreator.createUserSavedWithAccount())
                .build();
    }
}
