package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.models.transaction.Transaction;

import java.math.BigDecimal;

public class StatementCreator {

    public static Transaction createTransferStatement(){
        return Transaction.createTransferTransaction(
                new BigDecimal(500),
                BankAccountCreator.createBankAccountSaved(),
                BankAccountCreator.createAnotherBankAccountSaved(),
                "whatever there"
        );
    }

    public static Transaction createDepositStatement(){
        return Transaction.createDepositTransaction(
                new BigDecimal(500),
                BankAccountCreator.createBankAccountSaved()
        );
    }
}
