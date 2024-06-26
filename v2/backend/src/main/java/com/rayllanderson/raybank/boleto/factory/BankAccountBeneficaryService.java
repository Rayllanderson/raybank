package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacadeInput;
import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditInput;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.ACCOUNT_INACTAVED;

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
    public String getName(final String id) {
        return bankAccountGateway.findByIdOrNumber(id).getAccountName();
    }

    @Override
    public void validate(final Beneficiary beneficiary) {
        final var account = (BankAccount) beneficiary.getData();

        if (!account.isActive()) {
            throw UnprocessableEntityException.with(ACCOUNT_INACTAVED, "Não é possível gerar boletos para conta bancária inativa");
        }
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
