package com.rayllanderson.raybank.external.boleto.response;

import com.rayllanderson.raybank.external.boleto.model.Boleto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class GenerateBoletoResponse {

    private final String code;
    private final BigDecimal value;
    private final LocalDate creationDate;
    private final LocalDate expirationDate;
    private final String institution = Boleto.PAYMENT_INSTITUTION;
    private final GenerateBoletoHolderResponse holder;

    public static GenerateBoletoResponse fromModel(Boleto boleto) {
        return new GenerateBoletoResponse(boleto.getCode(),
                boleto.getValue(),
                boleto.getCreationDate(),
                boleto.getExpirationDate(),
                GenerateBoletoHolderResponse.fromModel(boleto.getHolder())
        );
    }
}
