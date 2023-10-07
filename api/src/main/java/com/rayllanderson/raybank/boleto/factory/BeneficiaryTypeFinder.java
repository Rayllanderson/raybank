package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;

interface BeneficiaryTypeFinder {

    Beneficiary find(final String id);
    boolean supports(final BeneficiaryType type);
}
