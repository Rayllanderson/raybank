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
    private Long id;
    private Long cardNumber;
    private Integer securityCode;
    private YearMonth expiryDate;
    private BigDecimal balance;
    private BigDecimal invoice;

    public static CreditCardDto fromCreditCard(CreditCard creditCard){
        return new ModelMapper().map(creditCard, CreditCardDto.class);
    }
}
