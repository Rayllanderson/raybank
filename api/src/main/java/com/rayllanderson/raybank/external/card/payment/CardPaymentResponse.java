package com.rayllanderson.raybank.external.card.payment;

import com.rayllanderson.raybank.models.BankStatement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class CardPaymentResponse {
    private final String id;
    private final Instant moment;
    private final String type;
    private final BigDecimal amount;

    public static CardPaymentResponse fromBankStatement(final BankStatement bankStatement) {
        final var amount = bankStatement.getAmount().abs();
        return new CardPaymentResponse(bankStatement.getId(), bankStatement.getMoment(), bankStatement.getType().name(), amount);
    }
}
