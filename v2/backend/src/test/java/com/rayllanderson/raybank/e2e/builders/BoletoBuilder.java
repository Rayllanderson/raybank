package com.rayllanderson.raybank.e2e.builders;

import com.rayllanderson.raybank.boleto.controllers.payment.BoletoPaymentRequest;
import com.rayllanderson.raybank.boleto.services.generate.GenerateBoletoInput;

import java.math.BigDecimal;

public class BoletoBuilder {

    public static GenerateBoletoInput buildInput(BigDecimal value, GenerateBoletoInput.BeneficiaryInput beneficiary, String accountHolderId) {
        GenerateBoletoInput input = new GenerateBoletoInput();
        input.setValue(value);
        input.setBeneficiary(beneficiary);
        input.setAccountHolderId(accountHolderId);
        return input;
    }

    public static BoletoPaymentRequest buildRequest(String bardCode) {
        BoletoPaymentRequest r = new BoletoPaymentRequest();
        r.setBarCode(bardCode);
        return r;
    }
}
