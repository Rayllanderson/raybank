package com.rayllanderson.raybank.card.services.payment.strategies;

import com.rayllanderson.raybank.card.events.CardCreditPaymentCompletedEvent;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.event.IntegrationEventPublisher;
import com.rayllanderson.raybank.installment.services.create.CreateInstallmentPlanMapper;
import com.rayllanderson.raybank.installment.services.create.CreateInstallmentPlanService;
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
    private final CreateInstallmentPlanMapper planMapper;
    private final CreateInstallmentPlanService createInstallmentPlanService;

    @Override
    @Transactional
    public Transaction pay(final PaymentCardInput payment, final Card card) {
        final var transaction = transactionRepository.save(CardCreditPaymentTransaction.from(payment, card));

        card.pay(payment.toCreditCardPayment());

        final var createPlanInput = planMapper.from(transaction);
        final var installmentPlan = createInstallmentPlanService.create(createPlanInput);
        transaction.addPlan(installmentPlan.getId());

        eventPublisher.publish(new CardCreditPaymentCompletedEvent(transaction));

        return transaction;
    }

    @Override
    public boolean supports(PaymentCardInput.PaymentType paymentType) {
        return PaymentCardInput.PaymentType.CREDIT.equals(paymentType);
    }
}
