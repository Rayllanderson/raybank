package com.rayllanderson.raybank.bankaccount.services.find.strategies;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.services.find.FindAccountType;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.statement.factory.PixBankStatementFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAccountPixStrategy implements FindAccountStrategy {

    private final PixGateway pixGateway;

    @Override
    public BankAccount find(String value) {
        return pixGateway.findKeyByKey(value).getBankAccount();
    }

    @Override
    public boolean supports(FindAccountType type) {
        return FindAccountType.PIX.equals(type);
    }
}
