package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.services.generate.GenerateBoletoInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BeneficiaryFactory {

    private final List<BeneficiaryTypeFinder> finders;

    public Beneficiary getBeneficiaryFrom(final GenerateBoletoInput.BeneficiaryInput beneficiaryInput){
        return getBeneficiaryByIdAndType(beneficiaryInput.getId(), beneficiaryInput.getType());
    }

    public Beneficiary getBeneficiaryDataFrom(final Beneficiary beneficiary) {
        return getBeneficiaryByIdAndType(beneficiary.getId(), beneficiary.getType());
    }

    public Beneficiary getBeneficiaryByIdAndType(final String id, final BeneficiaryType type){
        return finders.stream()
                .filter(f -> f.supports(type))
                .findFirst()
                .orElseThrow(BeneficiaryTypeFinderNotFoundException::new)
                .find(id);
    }

    private static class BeneficiaryTypeFinderNotFoundException extends RuntimeException {
        public BeneficiaryTypeFinderNotFoundException() {
            super("No strategies were found for beneficiary finder");
        }
    }
}
