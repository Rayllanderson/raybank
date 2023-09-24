package com.rayllanderson.raybank.card.services.payment.strategies;

import com.rayllanderson.raybank.card.events.CardPaymentCompletedEvent;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.card.transactions.payment.CardPaymentTransaction;
import com.rayllanderson.raybank.event.IntegrationEventPublisher;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditCardPaymentStrategy implements CardPaymentStrategy {

    private final IntegrationEventPublisher eventPublisher;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction pay(final PaymentCardInput payment, final Card card) {

        card.pay(payment.toCreditCardPayment());

        final var transaction = transactionRepository.save(CardPaymentTransaction.from(payment, card));
        eventPublisher.publish(new CardPaymentCompletedEvent(transaction));

        return transaction;
    }

    @Override
    public boolean supports(PaymentCardInput.PaymentType paymentType) {
        return PaymentCardInput.PaymentType.CREDIT.equals(paymentType);
    }
}
