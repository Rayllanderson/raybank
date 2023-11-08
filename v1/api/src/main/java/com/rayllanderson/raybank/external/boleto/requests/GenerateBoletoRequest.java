package com.rayllanderson.raybank.external.boleto.requests;

import com.rayllanderson.raybank.external.boleto.model.Boleto;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
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

    @URL
    @NotBlank
    private final String postBackUrl;

    @NotBlank
    private final String requester;

    public GenerateBoletoRequest(BigDecimal value, String postBackUrl, String requester, GenerateBoletoHolderRequest holder) {
        this.value = value;
        this.holder = holder;
        this.postBackUrl = postBackUrl;
        this.requester = requester;
    }

    public Boleto toModel() {
        return Boleto.generate(this.value, this.postBackUrl, requester, holder.toModel());
    }
}
