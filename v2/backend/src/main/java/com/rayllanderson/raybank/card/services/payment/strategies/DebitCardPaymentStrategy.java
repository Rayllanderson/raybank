package com.rayllanderson.raybank.card.services.payment.strategies;

import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacadeInput;
import com.rayllanderson.raybank.card.events.CardDebitPaymentCompletedEvent;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.CardPaymentInput;
import com.rayllanderson.raybank.shared.event.IntegrationEventPublisher;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DebitCardPaymentStrategy implements CardPaymentStrategy {

    private final DebitAccountFacade debitAccountFacade;
    private final IntegrationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Transaction pay(final CardPaymentInput payment, final Card card) {

        final var debit = DebitAccountFacadeInput.debitCardPayment(payment, card);
        final var debitTransaction = debitAccountFacade.process(debit);

        eventPublisher.publish(new CardDebitPaymentCompletedEvent(debitTransaction));

        return debitTransaction;
    }

    @Override
    public boolean supports(CardPaymentInput.PaymentType paymentType) {
        return CardPaymentInput.PaymentType.DEBIT.equals(paymentType);
    }
}
