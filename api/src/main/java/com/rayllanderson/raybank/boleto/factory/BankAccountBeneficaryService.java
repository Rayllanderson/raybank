package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacadeInput;
import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditInput;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BankAccountBeneficary implements BeneficiaryTypeFinder {

    private final BankAccountGateway bankAccountGateway;
    private final CreditAccountFacade creditAccountFacade;

    @Override
    public Beneficiary find(final String id) {
        final var bankAccount = bankAccountGateway.findById(id);

        return new Beneficiary(bankAccount.getId(), BeneficiaryType.ACCOUNT, bankAccount);
    }

    @Override
    public Transaction receiveCredit(BoletoCreditInput input) {
        final var credit = CreditAccountFacadeInput.from(input);
        return creditAccountFacade.process(credit);
    }

    @Override
    public boolean supports(final BeneficiaryType type) {
        return BeneficiaryType.ACCOUNT.equals(type);
    }
}
