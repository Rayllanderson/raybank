package com.rayllanderson.raybank.card.services.credit;

import com.rayllanderson.raybank.card.models.inputs.CreditInput;
import com.rayllanderson.raybank.shared.dtos.Origin;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@Setter
public class CardCreditInput {

    private String cardId;
    private BigDecimal amount;
    private Origin origin;

    public CreditInput toDomainInput() {
        return new ModelMapper().map(this, CreditInput.class);
    }
}
