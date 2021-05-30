package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.models.BankAccount;

public class BankAccountCreator {

    public static BankAccount createBankAccountSaved(){
        return BankAccount.builder()
                .id(1L)
                .accountNumber(999999999)
                .user(UserCreator.createUserWithId())
                .creditCard(CreditCardCreator.createCreditCardSaved())
                .build();
    }
}
