package com.rayllanderson.raybank.card.services.credit;

import com.rayllanderson.raybank.card.models.inputs.CreditInput;
import com.rayllanderson.raybank.card.models.inputs.CreditOriginType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class CardCreditInput {

    private final String cardId;
    private final BigDecimal amount;
    private final CreditOrigin origin;

    @Getter
    @RequiredArgsConstructor
    public static class CreditOrigin {
        private final String identifier;
        private final CreditOriginType type;
    }

    public CreditInput toDomainInput() {
        return new ModelMapper().map(this, CreditInput.class);
    }
}
