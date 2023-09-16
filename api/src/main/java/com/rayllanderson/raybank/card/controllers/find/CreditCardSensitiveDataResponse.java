package com.rayllanderson.raybank.card.controllers.find;

import com.rayllanderson.raybank.card.models.CreditCard;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
public class CreditCardSensitiveDataResponse {
    private String id;
    private Long number;
    private Integer securityCode;
    private YearMonth expiryDate;
    private BigDecimal limit;
    private BigDecimal balance;

    public static CreditCardSensitiveDataResponse fromCreditCard(CreditCard c){
        return new ModelMapper().map(c, CreditCardSensitiveDataResponse.class);
    }
}
