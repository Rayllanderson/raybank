package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditInput;

interface BeneficiaryTypeService {

    Beneficiary find(final String id);
    void receiveCredit(final BoletoCreditInput input);
    boolean supports(final BeneficiaryType type);
}
