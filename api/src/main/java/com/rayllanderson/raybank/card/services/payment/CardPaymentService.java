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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardPaymentService {

    private final BankAccountRepository bankAccountRepository;
    private final CardRepository cardRepository;
    private final CardPaymentStrategyFactory cardPaymentStrategyFactory;

    @Transactional
    public Transaction pay(final PaymentCardInput payment) {
        final Card card = cardRepository.findByNumber(payment.getCardNumber())
                .orElseThrow(() -> new NotFoundException("Cartão de crédito inexistente"));

        validateCvvAndExpiryDate(payment, card);

        final var establishmentAccount = getEstablishmentAccount(payment);
        if (establishmentAccount.sameCard(card)) {
            throw new UnprocessableEntityException("Estabelecimento não pode receber pagamentos desse cartão");
        }

        final CardPaymentStrategy cardPaymentService = cardPaymentStrategyFactory.getStrategyBy(payment.getPaymentType());
        return cardPaymentService.pay(payment, card);
    }

    private BankAccount getEstablishmentAccount(final PaymentCardInput payment) {
        final var establishment = bankAccountRepository.findById(payment.getEstablishmentId())
                .orElseThrow(() -> new UnprocessableEntityException("Estabelecimento não pode receber pagamentos"));

        if (!establishment.isEstablishment()) {
            throw new UnprocessableEntityException("Estabelecimento não pode receber pagamentos");
        }

        return establishment;
    }

    private static void validateCvvAndExpiryDate(PaymentCardInput payment, Card card) {
        if (!card.isValidSecurityCode(payment.getCardSecurityCode())) {
            throw new UnprocessableEntityException("Código de Segurança Inválido");
        }
        if (!card.isValidExpiryDate(payment.getCardExpiryDate())) {
            throw new UnprocessableEntityException("Data de Vencimento Inválida");
        }
        if (card.isExpired())
            throw UnprocessableEntityException.with("Cartão está expirado");
    }
}
