package com.rayllanderson.raybank.bankaccount.gateway;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;

public interface BankAccountGateway {
    BankAccount findById(final String id);
}
