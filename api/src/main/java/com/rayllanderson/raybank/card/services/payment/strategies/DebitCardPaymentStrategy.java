package com.rayllanderson.raybank.card.services.payment.strategies;

import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacadeInput;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DebitCardPaymentStrategy implements CardPaymentStrategy {

    private final DebitAccountFacade debitAccountFacade;

    @Override
    @Transactional
    public Transaction pay(final PaymentCardInput payment, final Card card) {

        final var debit = DebitAccountFacadeInput.from(payment, card);
        return debitAccountFacade.process(debit);
    }

    @Override
    public boolean supports(PaymentCardInput.PaymentType paymentType) {
        return PaymentCardInput.PaymentType.DEBIT.equals(paymentType);
    }
}
