package com.rayllanderson.raybank.statement.factory;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Debit;
import com.rayllanderson.raybank.transaction.models.Debit.Origin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.transaction.models.Credit.Destination;

@Component
@RequiredArgsConstructor
public class BankAccountBankStatementFinder implements BankStatementFinder {

    private final BankAccountGateway gateway;

    @Override
    public BankStatement.Credit find(Credit credit) {
        final var account = gateway.findByIdOrNumber(credit.getId());
        return new BankStatement.Credit(account.getId(), account.getAccountName(), credit.getDestination().name());
    }

    @Override
    public BankStatement.Debit find(Debit debit) {
        final var account = gateway.findByIdOrNumber(debit.getId());
        return new BankStatement.Debit(account.getId(), account.getAccountName(), debit.getOrigin().name());
    }

    @Override
    public boolean supports(Origin debitOrigin) {
        return Origin.ACCOUNT.equals(debitOrigin) || Origin.ESTABLISHMENT_ACCOUNT.equals(debitOrigin) || Origin.DEBIT_CARD.equals(debitOrigin);
    }

    @Override
    public boolean supports(Destination destination) {
        return Destination.ACCOUNT.equals(destination) || Destination.ESTABLISHMENT_ACCOUNT.equals(destination);
    }
}
