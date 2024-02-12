package com.rayllanderson.raybank.bankaccount.services.find.strategies;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.services.find.FindAccountType;

public interface FindAccountStrategy {
    BankAccount find(String value);
    boolean supports(FindAccountType type);
}
