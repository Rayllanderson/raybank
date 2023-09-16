package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.statement.models.BankStatement;

import java.math.BigDecimal;

public class StatementCreator {

    public static BankStatement createTransferStatement(){
        return BankStatement.createTransferBankStatement(
                new BigDecimal(500),
                BankAccountCreator.createBankAccountSaved(),
                BankAccountCreator.createAnotherBankAccountSaved(),
                "whatever there"
        );
    }

    public static BankStatement createDepositStatement(){
        return BankStatement.createDepositBankStatement(
                new BigDecimal(500),
                BankAccountCreator.createBankAccountSaved()
        );
    }
}
