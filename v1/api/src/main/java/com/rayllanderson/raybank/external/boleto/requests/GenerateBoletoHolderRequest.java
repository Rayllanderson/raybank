package com.rayllanderson.raybank.external.boleto.requests;

import com.rayllanderson.raybank.external.boleto.model.BoletoHolder;
import com.rayllanderson.raybank.external.validator.CPFOrCNPJ;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@RequiredArgsConstructor
public class GenerateBoletoHolderRequest {
    @NotEmpty
    private final String name;

    @NotEmpty
    @CPFOrCNPJ
    private final String document;

    public BoletoHolder toModel() {
        return new BoletoHolder(this.name, this.document);
    }
}
