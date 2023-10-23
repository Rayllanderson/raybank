package com.rayllanderson.raybank.contact.factory;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.contact.service.ContactAccountType;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PixContactAccountFinder implements ContactAccountFinder {

    private final PixGateway pixGateway;

    @Override
    public BankAccount find(String i) {
        final Pix pix = pixGateway.findPixById(i);

        return pix.getCredit().getBankAccount();
    }

    @Override
    public boolean supports(TransactionMethod method) {
        return TransactionMethod.PIX.equals(method);
    }
}
