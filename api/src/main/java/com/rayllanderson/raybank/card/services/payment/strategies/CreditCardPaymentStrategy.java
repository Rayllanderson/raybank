package com.rayllanderson.raybank.card.services.payment.strategies;

import com.rayllanderson.raybank.card.events.CardCreditPaymentCompletedEvent;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.limit.FindCardLimitService;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.installment.services.create.CreateInstallmentPlanMapper;
import com.rayllanderson.raybank.installment.services.create.CreateInstallmentPlanService;
import com.rayllanderson.raybank.shared.event.IntegrationEventPublisher;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INSUFFICIENT_CARD_LIMIT;

@Service
@RequiredArgsConstructor
public class CreditCardPaymentStrategy implements CardPaymentStrategy {

    private final IntegrationEventPublisher eventPublisher;
    private final TransactionGateway transactionGateway;
    private final FindCardLimitService cardLimitService;
    private final CreateInstallmentPlanMapper planMapper;
    private final CreateInstallmentPlanService createInstallmentPlanService;

    @Override
    @Transactional
    public Transaction pay(final PaymentCardInput payment, final Card card) {
        final var transaction = CardCreditPaymentTransaction.from(payment, card);

        final boolean hasAvailableLimit = cardLimitService.hasAvailableLimit(card, payment.getAmount());
        if (!hasAvailableLimit) {
            throw UnprocessableEntityException.with(INSUFFICIENT_CARD_LIMIT, "Seu cartão não possui limite suficiente para esta compra.");
        }

        final var createPlanInput = planMapper.from(transaction);
        final var installmentPlan = createInstallmentPlanService.create(createPlanInput);
        transaction.addPlan(installmentPlan.getId());

        eventPublisher.publish(new CardCreditPaymentCompletedEvent(transaction));

        transactionGateway.save(transaction);
        return transaction;
    }

    @Override
    public boolean supports(PaymentCardInput.PaymentType paymentType) {
        return PaymentCardInput.PaymentType.CREDIT.equals(paymentType);
    }
}
