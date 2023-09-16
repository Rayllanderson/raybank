package com.rayllanderson.raybank.external.card.payment;

import com.rayllanderson.raybank.models.statements.BankStatement;
import com.rayllanderson.raybank.models.transaction.CardTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CardPaymentResponse {
    private final String id;
    private final LocalDateTime moment;
    private final String type;
    private final BigDecimal amount;

    public static CardPaymentResponse fromBankStatement(final BankStatement bankStatement) {
        final var amount = bankStatement.getAmount().abs();
        return new CardPaymentResponse(bankStatement.getId(), null, bankStatement.getType().name(), amount);
    }

    public static CardPaymentResponse fromTransaction(final CardTransaction bankStatement) {
        return new CardPaymentResponse(bankStatement.getId(), bankStatement.getMoment(), bankStatement.getPaymentType().name(), bankStatement.getAmount());
    }
}
