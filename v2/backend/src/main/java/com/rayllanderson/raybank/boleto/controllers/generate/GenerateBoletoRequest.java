package com.rayllanderson.raybank.boleto.controllers.generate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.BOLETO_BENEFICIARY_TYPE_BADLY_FORMATTED;

@Getter
@Setter
public class GenerateBoletoRequest {
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal value;

    private String title;

    @NotBlank
    private String accountHolderId;
    @Valid
    @NotNull
    private BeneficiaryInput beneficiary;

    @Getter
    @Setter
    public static class BeneficiaryInput {
        @NotBlank
        private String id;
        @NotNull
        private BeneficiaryType type;

        enum BeneficiaryType {
            INVOICE, ACCOUNT;

            @JsonCreator
            static BeneficiaryType from(String type) {
                try {
                    return BeneficiaryType.valueOf(type.toUpperCase());
                } catch (final Exception e) {
                    throw BadRequestException.withFormatted(BOLETO_BENEFICIARY_TYPE_BADLY_FORMATTED, "Invalid type. Availables Types are %s", Arrays.toString(values()));
                }
            }
        }
    }
}
