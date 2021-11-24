package com.rayllanderson.raybank.external.boleto.requests;

import com.rayllanderson.raybank.external.boleto.model.Boleto;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
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

    public GenerateBoletoRequest(BigDecimal value, @Valid GenerateBoletoHolderRequest holder) {
        this.value = value;
        this.holder = holder;
    }

    public Boleto generateBoleto() {
        return Boleto.generate(this.value, holder.toModel());
    }
}
