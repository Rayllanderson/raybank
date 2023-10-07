package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BankAccountBeneficary implements BeneficiaryTypeFinder {

    private final BankAccountGateway bankAccountGateway;

    @Override
    public Beneficiary find(final String id) {
        final var bankAccount = bankAccountGateway.findById(id);

        return new Beneficiary(bankAccount.getId(), BeneficiaryType.BANK_ACCOUNT);
    }

    @Override
    public boolean supports(final BeneficiaryType type) {
        return BeneficiaryType.BANK_ACCOUNT.equals(type);
    }
}
