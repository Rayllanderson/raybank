package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditInput;
import com.rayllanderson.raybank.boleto.services.generate.GenerateBoletoInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BeneficiaryFactory {

    private final List<BeneficiaryTypeService> services;

    public Beneficiary getBeneficiaryAndValidate(final GenerateBoletoInput.BeneficiaryInput beneficiaryInput){
        final BeneficiaryTypeService beneficiaryTypeService = getBeneficiaryTypeService(beneficiaryInput.getType());

        final Beneficiary beneficiary = beneficiaryTypeService.find(beneficiaryInput.getId());
        beneficiaryTypeService.validate(beneficiary);

        return beneficiary;
    }

    public Beneficiary getBeneficiaryDataFrom(final Beneficiary beneficiary) {
        return getBeneficiaryByIdAndType(beneficiary.getId(), beneficiary.getType());
    }

    public Beneficiary getBeneficiaryByIdAndType(final String id, final BeneficiaryType type){
        return getBeneficiaryTypeService(type).find(id);
    }

    public String getBeneficiaryName(final String id, final BeneficiaryType type){
        return getBeneficiaryTypeService(type).getName(id);
    }

    public void receiveCredit(final BoletoCreditInput credit) {
        getBeneficiaryTypeService(BeneficiaryType.valueOf(credit.getBeneficiaryType()))
                .receiveCredit(credit);
    }

    private BeneficiaryTypeService getBeneficiaryTypeService(BeneficiaryType type) {
        return services.stream()
                .filter(f -> f.supports(type))
                .findFirst()
                .orElseThrow(BeneficiaryTypeServiceNotFoundException::new);
    }

    private static class BeneficiaryTypeServiceNotFoundException extends RuntimeException {
        public BeneficiaryTypeServiceNotFoundException() {
            super("No strategies were found for beneficiary service");
        }
    }
}
