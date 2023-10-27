package com.rayllanderson.raybank.statement.factory;

import com.rayllanderson.raybank.card.gateway.CardGateway;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Debit;
import com.rayllanderson.raybank.transaction.models.Debit.Origin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.transaction.models.Credit.Destination;

@Component
@RequiredArgsConstructor
public class CardBankStatementFinder implements BankStatementFinder {

    private final CardGateway gateway;
    public static final String NAME = "Cartão de Crédito";

    @Override
    public BankStatement.Credit find(Credit credit) {
        final var obj = gateway.findById(credit.getId());
        return new BankStatement.Credit(obj.getId(), NAME, credit.getDestination().name());
    }

    @Override
    public BankStatement.Debit find(Debit debit) {
        final var obj = gateway.findById(debit.getId());
        return new BankStatement.Debit(obj.getId(), NAME, debit.getOrigin().name());
    }

    @Override
    public boolean supports(Origin debitOrigin) {
        return Origin.CREDIT_CARD.equals(debitOrigin) ;
    }

    @Override
    public boolean supports(Destination destination) {
        return Destination.CREDIT_CARD.equals(destination);
    }
}
