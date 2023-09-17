package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.bankaccount.controllers.requests.BankDepositDto;

import java.math.BigDecimal;

public class BankDepositCreator {

    public static BankDepositDto createBankDepositDto(String ownerId){
       return BankDepositDto.builder()
                .amount(new BigDecimal("400.00"))
                .ownerId(ownerId)
                .build();
    }

    public static BankDepositDto createBankDepositDto(String ownerId, BigDecimal toDeposit){
        return BankDepositDto.builder()
                .amount(toDeposit)
                .ownerId(ownerId)
                .build();
    }

    /**
     * @return amount(BigDecimal.ZERO)
     */
    public static BankDepositDto createAnInvalidBankDepositDto(String ownerId){
        return BankDepositDto.builder()
                .amount(BigDecimal.ZERO)
                .ownerId(ownerId)
                .build();
    }
}
