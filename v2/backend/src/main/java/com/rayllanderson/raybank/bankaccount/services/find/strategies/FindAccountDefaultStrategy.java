package com.rayllanderson.raybank.bankaccount.services.find.strategies;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.services.find.FindAccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAccountDefaultStrategy implements FindAccountStrategy {
    private final BankAccountGateway bankAccountGateway;

    @Override
    public BankAccount find(String value) {
        return bankAccountGateway.findByIdOrNumber(value);
    }

    @Override
    public boolean supports(FindAccountType type) {
        return FindAccountType.ACCOUNT.equals(type);
    }
}
