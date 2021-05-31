package com.rayllanderson.raybank.dtos.responses;

import com.rayllanderson.raybank.models.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDto {
    private Long id;
    private Long cardNumber;

    public static CreditCardDto fromCreditCard(CreditCard creditCard){
        return new ModelMapper().map(creditCard, CreditCardDto.class);
    }
}
