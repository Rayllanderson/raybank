package com.rayllanderson.raybank.card.controllers.find;

import com.rayllanderson.raybank.card.services.find.CardDetailsOutput;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
public class CardDetailsSensitiveResponse {
    private String id;
    private String number;
    private Integer securityCode;
    private YearMonth expiryDate;
    private BigDecimal limit;
    private BigDecimal usedLimit;
    private BigDecimal availableLimit;

    public static CardDetailsSensitiveResponse from(CardDetailsOutput c){
        return new ModelMapper().map(c, CardDetailsSensitiveResponse.class);
    }
}
