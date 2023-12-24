package com.rayllanderson.raybank.bankaccount.services.find;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAccountService {

    private final FindAccountMapper findAccountMapper;
    private final BankAccountGateway bankAccountGateway;

    public BankAccountDetailsOutput findById(final String id) {
        final BankAccount bankAccount = bankAccountGateway.findById(id);
        return findAccountMapper.from(bankAccount);
    }
}
