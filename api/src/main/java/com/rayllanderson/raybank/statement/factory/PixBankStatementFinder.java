package com.rayllanderson.raybank.statement.factory;

import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Debit;
import com.rayllanderson.raybank.transaction.models.Debit.Origin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.transaction.models.Credit.Destination;

@Component
@RequiredArgsConstructor
public class PixBankStatementFinder implements BankStatementFinder {

    private final PixGateway gateway;

    @Override
    public BankStatement.Credit find(Credit credit) {
        Pix pix = getPix(credit.getId());

        return new BankStatement.Credit(pix.getId(), pix.getCreditName(), credit.getDestination().name());
    }

    @Override
    public BankStatement.Debit find(Debit debit) {
        final var obj = getPix(debit.getId());
        return new BankStatement.Debit(obj.getId(), obj.getDebitName(), debit.getOrigin().name());
    }

    private Pix getPix(String id) {
        boolean isPixReturn = id.startsWith("D");

        Pix pix;

        if (isPixReturn) {
            final var obj2 = gateway.findPixReturnById(id);
            pix = obj2.getOrigin();
        } else {
            pix = gateway.findPixById(id);
        }
        return pix;
    }

    @Override
    public boolean supports(Origin debitOrigin) {
        return Origin.PIX.equals(debitOrigin) ;
    }

    @Override
    public boolean supports(Destination destination) {
        return Destination.PIX.equals(destination);
    }
}
