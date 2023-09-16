package com.rayllanderson.raybank.controllers.creditcard.responses;

import com.rayllanderson.raybank.statement.models.BankStatement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class PayInvoiceInternalResponse {
    private final String id;
    private final Instant moment;
    private final String type;
    private final BigDecimal amount;

    public static PayInvoiceInternalResponse fromBankStatement(final BankStatement bankStatement) {
        final var amount = bankStatement.getAmount().abs();
        return new PayInvoiceInternalResponse(bankStatement.getId(), bankStatement.getMoment(), bankStatement.getType().name(), amount);
    }
}
