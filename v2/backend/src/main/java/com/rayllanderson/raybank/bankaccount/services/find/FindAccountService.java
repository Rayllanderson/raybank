package com.rayllanderson.raybank.bankaccount.services.find;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.services.find.strategies.FindAccountStrategy;
import com.rayllanderson.raybank.bankaccount.services.find.strategies.FindAccountStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAccountService {

    private final FindAccountMapper findAccountMapper;
    private final FindAccountStrategyFactory findAccountStrategyFactory;
    private final BankAccountGateway bankAccountGateway;

    public BankAccountDetailsOutput findById(final String id) {
        final BankAccount bankAccount = bankAccountGateway.findById(id);
        return findAccountMapper.from(bankAccount);
    }

    public BankAccountOutput findByType(final FindAccountType type, String value) {
        FindAccountStrategy finderStrategy = findAccountStrategyFactory.getFinderStrategy(type);

        final BankAccount bankAccount = finderStrategy.find(value);

        return findAccountMapper.mapFromBankAccount(bankAccount);
    }
}
