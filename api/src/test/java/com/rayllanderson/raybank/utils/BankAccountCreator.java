package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;

import java.math.BigDecimal;

public class BankAccountCreator {

    public static BankAccount createBankAccountSaved(){
        return null;
//        return new BankAccount(
//                "1",
//                999999999,
//                new BigDecimal(500),
//                CreditCardCreator.createCreditCardSaved(),
//                UserCreator.createUserWithId(),
//                new HashSet<>()
//        );
    }

    public static BankAccount createAnotherBankAccountSaved(){
        return BankAccount.builder()
                .id("2")
                .number(999999999)
                .balance(new BigDecimal(500))
                .user(UserCreator.createUserWithId())
                .card(CreditCardCreator.createCreditCardSaved())
                .build();
    }

    public static BankAccount createBankAccountToBeSavedWithoutCreditCard(){
        return BankAccount.builder()
                .number(999999999)
                .user(UserCreator.createUserWithId())
                .balance(new BigDecimal(500))
                .build();
    }

    public static BankAccount createBankAccountToBeSavedWithoutCreditCardAndWithoutUser(){
        BankAccount account = new BankAccount();
        account.setBalance(BigDecimal.valueOf(800));
        account.setNumber(999889999);
        return account;
    }
}
