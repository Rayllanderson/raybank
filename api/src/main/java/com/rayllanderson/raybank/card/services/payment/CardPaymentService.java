package com.rayllanderson.raybank.card.services.payment;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.repository.CardRepository;
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

@Service
@RequiredArgsConstructor
public class CardPaymentService {

    private final BankAccountRepository bankAccountRepository;
    private final CardRepository cardRepository;
    private final CardPaymentStrategyFactory cardPaymentStrategyFactory;

    @Transactional
    @Retryable(
            noRetryFor = {NotFoundException.class, UnprocessableEntityException.class},
            maxAttemptsExpression = "${retry.card.payment.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.card.payment.maxDelay}"))
    public Transaction pay(final PaymentCardInput payment) {
        final Card card = cardRepository.findByNumber(payment.getCardNumber())
                .orElseThrow(() -> new NotFoundException("Cartão de crédito inexistente"));

        validate(payment, card);

        final var establishmentAccount = getEstablishmentAccount(payment);
        if (establishmentAccount.sameCard(card)) {
            throw new UnprocessableEntityException("Estabelecimento não pode receber pagamentos desse cartão");
        }

        final CardPaymentStrategy cardPaymentService = cardPaymentStrategyFactory.getStrategyBy(payment.getPaymentType());
        return cardPaymentService.pay(payment, card);
    }

    private BankAccount getEstablishmentAccount(final PaymentCardInput payment) {
        final var invalidEstablishmentException = new UnprocessableEntityException("Estabelecimento não pode receber pagamentos");

        final var establishment = bankAccountRepository.findById(payment.getEstablishmentId())
                .orElseThrow(() -> invalidEstablishmentException);

        if (!establishment.isEstablishment()) {
            throw invalidEstablishmentException;
        }

        if (!establishment.isActive()) {
            throw invalidEstablishmentException;
        }

        return establishment;
    }

    private static void validate(PaymentCardInput payment, Card card) {
        if (!card.isValidSecurityCode(payment.getCardSecurityCode())) {
            throw new UnprocessableEntityException("Código de Segurança Inválido");
        }
        if (!card.isValidExpiryDate(payment.getCardExpiryDate())) {
            throw new UnprocessableEntityException("Data de Vencimento Inválida");
        }
        if (card.isExpired())
            throw UnprocessableEntityException.with("Cartão está expirado");
        if (!card.isActive()) {
            throw new UnprocessableEntityException("Cartão não está ativo para compras");
        }
    }
}
