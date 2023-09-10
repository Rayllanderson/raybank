package com.rayllanderson.raybank.dtos.responses.bank;

import com.rayllanderson.raybank.models.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.YearMonth;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDto {
    private String id;
    private BigDecimal limit;
    private BigDecimal balance;
    private BigDecimal invoice;

    public static CreditCardDto fromCreditCard(CreditCard c){
        if (c == null) return null;
        return new CreditCardDto(c.getId(), c.getLimit(), c.getBalance(), c.getCurrentInvoice().getTotal());
    }
}
