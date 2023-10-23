package com.rayllanderson.raybank.contact.factory;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;

public interface ContactAccountFinder {
    BankAccount find(String i);
    boolean supports(TransactionMethod method);
}
