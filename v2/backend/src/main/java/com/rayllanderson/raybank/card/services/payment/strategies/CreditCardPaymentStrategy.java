package com.rayllanderson.raybank.card.services.payment.strategies;

import com.rayllanderson.raybank.card.events.CardCreditPaymentCompletedEvent;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.limit.FindCardLimitService;
import com.rayllanderson.raybank.card.services.payment.CardPaymentInput;
import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.installment.services.create.CreateInstallmentPlanInput;
import com.rayllanderson.raybank.installment.services.create.CreateInstallmentPlanMapper;
import com.rayllanderson.raybank.installment.services.create.CreateInstallmentPlanOutput;
import com.rayllanderson.raybank.installment.services.create.CreateInstallmentPlanService;
import com.rayllanderson.raybank.invoice.facade.InvoicePaymentFacade;
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
    private final InvoicePaymentFacade invoicePaymentFacade;

    @Override
    @Transactional
    public Transaction pay(final CardPaymentInput payment, final Card card) {
        final boolean hasAvailableLimit = cardLimitService.hasAvailableLimit(card, payment.getAmount());

        if (!hasAvailableLimit) {
            throw UnprocessableEntityException.with(INSUFFICIENT_CARD_LIMIT, "Seu cartão não possui limite suficiente para esta compra.");
        }

        final var transaction = CardCreditPaymentTransaction.from(payment, card);

        invoicePaymentFacade.verifyPaymentFor(card);

        final CreateInstallmentPlanInput createPlanInput = planMapper.from(transaction);
        final CreateInstallmentPlanOutput installmentPlan = createInstallmentPlanService.create(createPlanInput);
        transaction.addPlan(installmentPlan.id());

        transactionGateway.save(transaction);

        eventPublisher.publish(new CardCreditPaymentCompletedEvent(transaction));

        return transaction;
    }

    @Override
    public boolean supports(CardPaymentInput.PaymentType paymentType) {
        return CardPaymentInput.PaymentType.CREDIT.equals(paymentType);
    }
}
