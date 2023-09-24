package com.rayllanderson.raybank.card.services.payment.strategies;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.transaction.models.Transaction;

public interface CardPaymentStrategy {

    Transaction pay(final PaymentCardInput paymentInput, final CreditCard card);
    boolean supports(final PaymentCardInput.PaymentType paymentType);
}
