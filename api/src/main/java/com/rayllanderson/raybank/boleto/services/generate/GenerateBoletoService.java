package com.rayllanderson.raybank.boleto.services.generate;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.boleto.BoletoUtil;
import com.rayllanderson.raybank.boleto.factory.BeneficiaryFactory;
import com.rayllanderson.raybank.boleto.gateway.BoletoGateway;
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
        final var beneficiary = beneficiaryFactory.getBeneficiaryFrom(input.getBeneficiary());

        final var barCode = BoletoUtil.generateBarCode(input.getValue());
        final Boleto boleto = Boleto.generate(barCode, input.getValue(), beneficiary, holderAccount);
        boletoGateway.save(boleto);

        return GenerateBoletoOutput.from(boleto);
    }
}
