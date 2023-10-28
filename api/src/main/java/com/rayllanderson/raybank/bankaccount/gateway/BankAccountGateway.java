package com.rayllanderson.raybank.bankaccount.gateway;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;

public interface BankAccountGateway {
    BankAccount save(BankAccount bankAccount);
    void flush();

    BankAccount findById(final String id);

    BankAccount findByIdOrNumber(final String idOrNumber);

    boolean existsByNumber(int number);

    BankAccount findByNumber(int number);
}
