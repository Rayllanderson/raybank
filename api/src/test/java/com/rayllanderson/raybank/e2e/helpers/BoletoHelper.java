package com.rayllanderson.raybank.e2e.helpers;

import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.services.generate.GenerateBoletoInput;
import com.rayllanderson.raybank.boleto.services.generate.GenerateBoletoOutput;
import com.rayllanderson.raybank.boleto.services.generate.GenerateBoletoService;
import com.rayllanderson.raybank.e2e.builders.BoletoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BoletoHelper {

    @Autowired
    private GenerateBoletoService boletoService;

    public GenerateBoletoOutput generateBoleto(BigDecimal value, String beneficiaryId, BeneficiaryType beneficiaryType, String accountHolderId) {
        final var b = new GenerateBoletoInput.BeneficiaryInput();
        b.setId(beneficiaryId);
        b.setType(beneficiaryType);
        final var input = BoletoBuilder.buildInput(value, b, accountHolderId);
        return boletoService.generate(input);
    }
}
