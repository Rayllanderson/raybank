package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditInput;
import com.rayllanderson.raybank.transaction.models.Transaction;

interface BeneficiaryTypeFinder {

    Beneficiary find(final String id);
    Transaction receiveCredit(final BoletoCreditInput input);
    boolean supports(final BeneficiaryType type);
}
