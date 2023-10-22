package com.rayllanderson.raybank.bankaccount.gateway;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;

public interface BankAccountGateway {
    BankAccount save(BankAccount bankAccount);

    BankAccount findById(final String id);

    boolean existsByNumber(int number);

    BankAccount findByNumber(int number);
}
