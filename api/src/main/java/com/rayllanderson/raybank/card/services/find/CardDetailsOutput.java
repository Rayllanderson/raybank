package com.rayllanderson.raybank.card.services.find;

import com.rayllanderson.raybank.card.models.Card;
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
    private BigDecimal usedLimit;
    private BigDecimal availableLimit;

    public static CardDetailsOutput fromCreditCard(final Card c, final BigDecimal usedLimit, final BigDecimal availableLimit) {
        CardDetailsOutput cardDetailsOutput = new ModelMapper().map(c, CardDetailsOutput.class);
        cardDetailsOutput.usedLimit = usedLimit;
        cardDetailsOutput.availableLimit = availableLimit;
        return cardDetailsOutput;
    }
}
