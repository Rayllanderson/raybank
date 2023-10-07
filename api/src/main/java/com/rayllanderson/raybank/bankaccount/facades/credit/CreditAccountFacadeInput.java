package com.rayllanderson.raybank.bankaccount.facades.credit;

import com.rayllanderson.raybank.bankaccount.services.credit.CreditAccountInput;
import com.rayllanderson.raybank.card.events.CardCreditPaymentCompletedEvent;
import com.rayllanderson.raybank.shared.dtos.Origin;
import com.rayllanderson.raybank.shared.dtos.Type;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CreditAccountFacadeInput {

    private String accountId;
    private BigDecimal amount;
    private Origin origin;
    private final TransactionType transactionType;

    public CreditAccountInput toServiceInput() {
        return new ModelMapper().map(this, CreditAccountInput.class);
    }

    public static CreditAccountFacadeInput createFromCardPayment(String accountId, final BigDecimal amount, String cardId, String referenceTransactionId) {
        final var origin = new Origin(cardId, Type.CARD, referenceTransactionId);
        return new CreditAccountFacadeInput(accountId, amount, origin, TransactionType.CARD_RECEIVE_PAYMENT);
    }

    public static CreditAccountFacadeInput createFromCardPayment(CardCreditPaymentCompletedEvent event) {
        final var origin = new Origin(event.getCardId(), Type.CARD, event.getTransactionId());
        return new CreditAccountFacadeInput(event.getEstablishmentId(), event.getTotal(), origin, TransactionType.CARD_RECEIVE_PAYMENT);
    }
}
