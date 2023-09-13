package com.rayllanderson.raybank.external.boleto.requests;

import com.rayllanderson.raybank.external.boleto.model.Boleto;
import lombok.Getter;
import lombok.ToString;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@ToString
public class GenerateBoletoRequest {
    @NotNull
    @DecimalMin("0.1")
    private final BigDecimal value;

    @Valid
    @NotNull
    private final GenerateBoletoHolderRequest holder;

    @NotBlank
    private final String requesterAccountId;

    public GenerateBoletoRequest(BigDecimal value, String requesterAccountId, GenerateBoletoHolderRequest holder) {
        this.value = value;
        this.holder = holder;
        this.requesterAccountId = requesterAccountId;
    }

    public Boleto toModel(String postBackUrl) {
        return Boleto.generate(this.value, postBackUrl, requesterAccountId, holder.toModel());
    }
}
