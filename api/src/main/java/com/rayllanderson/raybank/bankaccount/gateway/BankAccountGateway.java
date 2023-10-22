package com.rayllanderson.raybank.bankaccount.gateway;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;

public interface BankAccountGateway {
    void save(BankAccount bankAccount);

    BankAccount findById(final String id);

    boolean existsByNumber(int number);
}
