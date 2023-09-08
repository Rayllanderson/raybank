package com.rayllanderson.raybank.external.boleto.requests;

import lombok.Getter;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;

@Getter
@ToString
public class PayBoletoRequest {
    @NotBlank
    private final String code;
    private Long ownerId;

    public PayBoletoRequest(String code) {
        this.code = code;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
