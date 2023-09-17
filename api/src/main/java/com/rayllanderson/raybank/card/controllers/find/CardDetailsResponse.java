package com.rayllanderson.raybank.card.controllers.find;

import com.rayllanderson.raybank.card.services.find.CardDetailsOutput;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@Setter
public class CardDetailsResponse {
    private String id;
    private BigDecimal limit;
    private BigDecimal balance;

    public static CardDetailsResponse from(CardDetailsOutput c){
        return new ModelMapper().map(c, CardDetailsResponse.class);
    }
}
