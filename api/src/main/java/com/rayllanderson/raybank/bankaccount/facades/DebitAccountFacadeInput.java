package com.rayllanderson.raybank.bankaccount.facades;

import com.rayllanderson.raybank.bankaccount.model.DebitOriginType;
import com.rayllanderson.raybank.bankaccount.services.DebitAccountInput;
import com.rayllanderson.raybank.invoice.services.payment.InvoicePaymentInput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class DebitAccountFacadeInput {

    private final String accountId;
    private final BigDecimal amount;
    private final DebitOrigin origin;

    @Getter
    @RequiredArgsConstructor
    public static class DebitOrigin {
        private final String identifier;
        private final DebitOriginType type;
    }

    public DebitAccountInput toServiceInput() {
        return new ModelMapper().map(this, DebitAccountInput.class);
    }

    public static DebitAccountFacadeInput from(InvoicePaymentInput input) {
        final var debitOrigin = new DebitAccountFacadeInput.DebitOrigin(input.getInvoiceId(), DebitOriginType.INVOICE_PAYMENT);
        return new DebitAccountFacadeInput(input.getAccountId(), input.getAmount(), debitOrigin);
    }
}
