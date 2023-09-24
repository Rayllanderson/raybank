package com.rayllanderson.raybank.card.services.payment.strategies;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.transaction.models.Transaction;

public interface CardPaymentStrategy {

    Transaction pay(final PaymentCardInput paymentInput, final Card card);
    boolean supports(final PaymentCardInput.PaymentType paymentType);
}
