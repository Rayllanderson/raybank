package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacadeInput;
import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BankAccountBeneficaryService implements BeneficiaryTypeService {

    private final BankAccountGateway bankAccountGateway;
    private final CreditAccountFacade creditAccountFacade;

    @Override
    public Beneficiary find(final String id) {
        final var bankAccount = bankAccountGateway.findById(id);

        return new Beneficiary(bankAccount.getId(), BeneficiaryType.ACCOUNT, bankAccount);
    }

    @Override
    public void receiveCredit(BoletoCreditInput input) {
        final var credit = CreditAccountFacadeInput.from(input);
        creditAccountFacade.process(credit);
    }

    @Override
    public boolean supports(final BeneficiaryType type) {
        return BeneficiaryType.ACCOUNT.equals(type);
    }
}
