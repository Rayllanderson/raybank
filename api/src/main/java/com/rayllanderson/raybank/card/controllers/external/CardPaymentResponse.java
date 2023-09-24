package com.rayllanderson.raybank.card.controllers.external;

import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.card.transactions.payment.CardPaymentTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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

    public static CardPaymentResponse fromTransaction(final CardPaymentTransaction transaction) {
        return new CardPaymentResponse(transaction.getId(), transaction.getMoment(), transaction.getType().name(), transaction.getAmount());
    }
}
