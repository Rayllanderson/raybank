package com.rayllanderson.raybank.card.services;

import com.rayllanderson.raybank.card.models.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDto {
    //TODO::deletar
    private String id;
    private BigDecimal limit;
    private BigDecimal balance;
    private BigDecimal invoice;

    public static CreditCardDto fromCreditCard(CreditCard c){
        if (c == null) return null;
        return new CreditCardDto(c.getId(), c.getLimit(), c.getBalance(), null);
    }
}
