package com.rayllanderson.raybank.e2e.builders;

import com.rayllanderson.raybank.bankaccount.controllers.transfer.BankAccountTransferRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BankAccountTransferRequestBuilder {

    public static BankAccountTransferRequest transferRequest(BigDecimal amount, String description, int beneficiaryAccountNumber) {
        final var r =  new BankAccountTransferRequest();
        r.setAmount(amount);
        r.setMessage(description);
        r.setBeneficiaryAccountNumber(beneficiaryAccountNumber);
        return r;
    }
}
