package com.rayllanderson.raybank.statement.factory;

import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Debit;
import com.rayllanderson.raybank.transaction.models.Debit.Origin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.transaction.models.Credit.Destination;

@Component
@RequiredArgsConstructor
public class InvoiceBankStatementFinder implements BankStatementFinder {

    public static final String NAME = "Fatura";

    @Override
    public BankStatement.Credit find(Credit credit) {
        return new BankStatement.Credit(credit.getId(), NAME, credit.getDestination().name());
    }

    @Override
    public BankStatement.Debit find(Debit debit) {
        return new BankStatement.Debit(debit.getId(), NAME, debit.getOrigin().name());
    }

    @Override
    public boolean supports(Origin debitOrigin) {
        return Origin.INVOICE.equals(debitOrigin);
    }

    @Override
    public boolean supports(Destination destination) {
        return Destination.INVOICE.equals(destination);
    }
}
