package com.rayllanderson.raybank.external.boleto.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.rayllanderson.raybank.external.boleto.model.BoletoHolder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonRootName("holder")
public class GenerateBoletoHolderResponse {
    private final String name;
    private final String document;

    public static GenerateBoletoHolderResponse fromModel(BoletoHolder holder) {
        return new GenerateBoletoHolderResponse(holder.getName(), holder.getDocument());
    }
}
