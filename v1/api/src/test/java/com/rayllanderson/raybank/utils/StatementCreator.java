package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.models.BankStatement;

import java.math.BigDecimal;

public class StatementCreator {

    public static BankStatement createTransferStatement(){
        return BankStatement.createTransferStatement(
                new BigDecimal(500),
                BankAccountCreator.createBankAccountSaved(),
                BankAccountCreator.createAnotherBankAccountSaved(),
                "whatever there"
        );
    }

    public static BankStatement createDepositStatement(){
        return BankStatement.createDepositStatement(
                new BigDecimal(500),
                BankAccountCreator.createBankAccountSaved()
        );
    }
}
