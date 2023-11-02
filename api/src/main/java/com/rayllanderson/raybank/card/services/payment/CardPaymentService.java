package com.rayllanderson.raybank.card.services.payment;

import com.rayllanderson.raybank.bankaccount.gateway.BankAccountGateway;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.gateway.CardGateway;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.strategies.CardPaymentStrategy;
import com.rayllanderson.raybank.card.services.payment.strategies.CardPaymentStrategyFactory;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.CARD_EXPIRED;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.CARD_INACTAVED;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.CARD_INVALID_EXPIRY_DATE;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.CARD_INVALID_SECURITY_CODE;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.ESTABLISHMENT_INVALID;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.ESTABLISHMENT_NOT_ACTIVE;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.ESTABLISHMENT_WITH_SAME_CARD;

@Service
@RequiredArgsConstructor
public class CardPaymentService {

    private final CardGateway cardGateway;
    private final BankAccountGateway bankAccountGateway;
    private final CardPaymentStrategyFactory cardPaymentStrategyFactory;

    @Transactional
    @Retryable(
            noRetryFor = {NotFoundException.class, UnprocessableEntityException.class},
            maxAttemptsExpression = "${retry.card.payment.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.card.payment.maxDelay}"))
    public Transaction pay(final CardPaymentInput payment) {
        final Card card = cardGateway.findByNumber(payment.getCardNumber());

        validate(payment, card);

        final var establishmentAccount = getEstablishmentAccount(payment);
        if (establishmentAccount.sameCard(card)) {
            throw UnprocessableEntityException.with(ESTABLISHMENT_WITH_SAME_CARD,"Estabelecimento não pode receber pagamentos desse cartão");
        }

        final CardPaymentStrategy cardPaymentService = cardPaymentStrategyFactory.getStrategyBy(payment.getPaymentType());
        return cardPaymentService.pay(payment, card);
    }

    private BankAccount getEstablishmentAccount(final CardPaymentInput payment) {
        final var establishment = bankAccountGateway.findById(payment.getEstablishmentId());

        if (!establishment.isEstablishment()) {
            throw UnprocessableEntityException.with(ESTABLISHMENT_INVALID, "Estabelecimento não pode receber pagamentos");
        }

        if (!establishment.isActive()) {
            throw UnprocessableEntityException.with(ESTABLISHMENT_NOT_ACTIVE, "Estabelecimento não pode receber pagamentos");
        }

        return establishment;
    }

    private static void validate(CardPaymentInput payment, Card card) {
        if (!card.isValidSecurityCode(payment.getCardSecurityCode())) {
            throw UnprocessableEntityException.with(CARD_INVALID_SECURITY_CODE, "Código de Segurança Inválido");
        }
        if (!card.isValidExpiryDate(payment.getCardExpiryDate())) {
            throw UnprocessableEntityException.with(CARD_INVALID_EXPIRY_DATE, "Data de Vencimento Inválida");
        }
        if (card.isExpired())
            throw UnprocessableEntityException.with(CARD_EXPIRED, "Cartão está expirado");
        if (!card.isActive()) {
            throw UnprocessableEntityException.with(CARD_INACTAVED, "Cartão não está ativo para compras");
        }
    }
}
