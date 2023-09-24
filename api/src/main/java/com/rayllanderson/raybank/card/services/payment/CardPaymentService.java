package com.rayllanderson.raybank.card.services.payment;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.events.CardPaymentCompletedEvent;
import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.card.models.inputs.CardPayment;
import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.card.transactions.payment.CardPaymentTransaction;
import com.rayllanderson.raybank.event.IntegrationEventPublisher;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import com.rayllanderson.raybank.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardPaymentService {

    private final UserRepository userRepository;
    private final IntegrationEventPublisher eventPublisher;
    private final CreditCardRepository creditCardRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public CardPaymentTransaction pay(final PaymentCardInput paymentInput) {
        final CreditCard card = creditCardRepository.findByNumber(paymentInput.getCardNumber())
                .orElseThrow(() -> new NotFoundException("Cartão de crédito inexistente"));

        validateCvvAndExpiryDate(paymentInput, card);

        final var establishmentAccount = getEstablishmentAccount(paymentInput);

        if (establishmentAccount.sameCard(card)) {
            throw new UnprocessableEntityException("Estabelecimento não pode receber pagamentos desse cartão");
        }

        final CardPayment payment = getCardPayment(paymentInput);
        card.pay(payment);

        final var transaction = transactionRepository.save(CardPaymentTransaction.from(paymentInput, card));

        eventPublisher.publish(new CardPaymentCompletedEvent(transaction));

        return transaction;
    }

    private CardPayment getCardPayment(PaymentCardInput payment) {
        return payment.isCreditPayment() ? payment.toCreditCardPayment() : payment.toDebitCardPayment();
    }

    private BankAccount getEstablishmentAccount(final PaymentCardInput payment) {
        final var establishment = userRepository.findById(payment.getEstablishmentId())
                .orElseThrow(() -> new UnprocessableEntityException("Estabelecimento não pode receber pagamentos"));

        if (!establishment.isEstablishment()) {
            throw new UnprocessableEntityException("Estabelecimento não pode receber pagamentos");
        }

        return establishment.getBankAccount();
    }

    private static void validateCvvAndExpiryDate(PaymentCardInput payment, CreditCard creditCard) {
        if (!creditCard.isValidSecurityCode(payment.getCardSecurityCode())) {
            throw new UnprocessableEntityException("Código de Segurança Inválido");
        }
        if (!creditCard.isValidExpiryDate(payment.getCardExpiryDate())) {
            throw new UnprocessableEntityException("Data de Vencimento Inválida");
        }
    }
}
