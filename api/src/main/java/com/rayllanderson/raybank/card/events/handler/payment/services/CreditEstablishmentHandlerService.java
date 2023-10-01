package com.rayllanderson.raybank.card.events.handler.payment.services;

import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacadeInput;
import com.rayllanderson.raybank.card.events.CardCreditPaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditEstablishmentHandlerService implements CardPaymentHandlerService {

    private final CreditAccountFacade creditAccountFacade;

    @Override
    public void process(final CardCreditPaymentCompletedEvent event) {
        final var credit = CreditAccountFacadeInput.createFromCardPayment(event);
        creditAccountFacade.process(credit);
    }
}
