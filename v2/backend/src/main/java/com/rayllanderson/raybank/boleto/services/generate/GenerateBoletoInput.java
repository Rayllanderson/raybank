package com.rayllanderson.raybank.boleto.services.generate;

import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GenerateBoletoInput {
    private BigDecimal value;
    private String title;
    private BeneficiaryInput beneficiary;
    private String accountHolderId;

    @Getter
    @Setter
    public static class BeneficiaryInput {
        private String id;
        private BeneficiaryType type;
    }
}
