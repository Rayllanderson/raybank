package com.rayllanderson.raybank.card.facades;

import com.rayllanderson.raybank.shared.dtos.Origin;
import com.rayllanderson.raybank.shared.dtos.Type;
import com.rayllanderson.raybank.card.services.credit.CardCreditInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CardCreditFacadeInput {

    private String cardId;
    private BigDecimal amount;
    private Origin origin;

    public CardCreditInput toServiceInput() {
        return new ModelMapper().map(this, CardCreditInput.class);
    }

    public static CardCreditFacadeInput createFromInvoicePayment(final BigDecimal amount, String cardId, String invoiceId, String referenceTransactionId) {
        final var origin = new Origin(invoiceId, Type.INVOICE, referenceTransactionId);
        return new CardCreditFacadeInput(cardId, amount, origin);
    }
}
