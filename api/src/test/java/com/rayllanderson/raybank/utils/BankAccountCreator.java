package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.models.BankAccount;

import java.math.BigDecimal;
import java.util.ArrayList;

public class BankAccountCreator {

    public static BankAccount createBankAccountSaved(){
        return new BankAccount(
                1L,
                999999999,
                new BigDecimal(500),
                CreditCardCreator.createCreditCardSaved(),
                UserCreator.createUserWithId(),
                new ArrayList<>()
        );
    }

    public static BankAccount createAnotherBankAccountSaved(){
        return BankAccount.builder()
                .id(2L)
                .accountNumber(999999999)
                .balance(new BigDecimal(500))
                .user(UserCreator.createUserWithId())
                .creditCard(CreditCardCreator.createCreditCardSaved())
                .build();
    }

    public static BankAccount createBankAccountToBeSavedWithoutCreditCard(){
        return BankAccount.builder()
                .accountNumber(999999999)
                .user(UserCreator.createUserWithId())
                .balance(new BigDecimal(500))
                .build();
    }

    public static BankAccount createBankAccountToBeSavedWithoutCreditCardAndWithoutUser(){
        return BankAccount.builder()
                .accountNumber(999999999)
                .balance(new BigDecimal(500))
                .build();
    }
}
