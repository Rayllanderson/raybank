package com.rayllanderson.raybank.contact.factory;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BankContactAccountFinder implements ContactAccountFinder {

    private final BankAccountGateway bankAccountGateway;

    @Override
    public BankAccount find(String i) {
        return bankAccountGateway.findByIdOrNumber(i);
    }

    @Override
    public boolean supports(TransactionMethod method) {
        return TransactionMethod.ACCOUNT_TRANSFER.equals(method);
    }
}
