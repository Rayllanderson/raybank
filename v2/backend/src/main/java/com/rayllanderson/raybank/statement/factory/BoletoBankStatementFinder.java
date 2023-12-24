package com.rayllanderson.raybank.statement.factory;

import com.rayllanderson.raybank.boleto.gateway.BoletoGateway;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Debit;
import com.rayllanderson.raybank.transaction.models.Debit.Origin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.transaction.models.Credit.Destination;

@Component
@RequiredArgsConstructor
public class BoletoBankStatementFinder implements BankStatementFinder {

    public static final String BOLETO = "Boleto";
    private final BoletoGateway gateway;

    @Override
    public BankStatement.Credit find(Credit credit) {
        final var obj = gateway.findByBarCode(credit.getId());
        return new BankStatement.Credit(credit.getId(), BOLETO, obj.getBeneficiary().getType().name());
    }

    @Override
    public BankStatement.Debit find(Debit debit) {
        return new BankStatement.Debit(debit.getId(), BOLETO, debit.getOrigin().name());
    }

    @Override
    public boolean supports(Origin debitOrigin) {
        return Origin.BOLETO.equals(debitOrigin) ;
    }

    @Override
    public boolean supports(Destination destination) {
        return Destination.BOLETO.equals(destination);
    }
}
