package com.rayllanderson.raybank.card.services.find;

import com.rayllanderson.raybank.card.models.CreditCard;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
public class CardDetailsOutput {
    private String id;
    private Long number;
    private Integer securityCode;
    private YearMonth expiryDate;
    private BigDecimal limit;
    private BigDecimal balance;

    public static CardDetailsOutput fromCreditCard(CreditCard c){
        return new ModelMapper().map(c, CardDetailsOutput.class);
    }
}
