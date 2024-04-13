package com.rayllanderson.raybank.boleto.services.generate;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.boleto.BoletoUtil;
import com.rayllanderson.raybank.boleto.factory.BeneficiaryFactory;
import com.rayllanderson.raybank.boleto.gateway.BoletoGateway;
import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.models.Boleto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenerateBoletoService {

    private final BoletoGateway boletoGateway;
    private final BankAccountGateway bankAccountGateway;
    private final BeneficiaryFactory beneficiaryFactory;

    @Transactional
    public GenerateBoletoOutput generate(final GenerateBoletoInput input) {
        final var holderAccount = bankAccountGateway.findById(input.getAccountHolderId());
        final var beneficiary = beneficiaryFactory.getBeneficiaryAndValidate(input.getBeneficiary());

        final String title = getTitle(input, beneficiary);

        final var barCode = BoletoUtil.generateBarCode(input.getValue());
        final Boleto boleto = Boleto.generate(barCode, title, input.getValue(), beneficiary, holderAccount);
        boletoGateway.save(boleto);

        return GenerateBoletoOutput.from(boleto);
    }

    private String getTitle(GenerateBoletoInput input, Beneficiary beneficiary) {
        if (input.getTitle() != null) {
            return input.getTitle();
        }
        final var beneficiaryData = beneficiary.getData();
        if (beneficiary.getType().equals(BeneficiaryType.ACCOUNT)) {
            final BankAccount account = (BankAccount) beneficiaryData;
            return account.getAccountName();
        } else if (beneficiary.getType().equals(BeneficiaryType.INVOICE)) {
            return "Fatura do cartão";
        }
        return null;
    }
}
