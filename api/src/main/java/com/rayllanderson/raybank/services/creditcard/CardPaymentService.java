package com.rayllanderson.raybank.services.creditcard;

import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.CreditCard;
import com.rayllanderson.raybank.models.inputs.CardPayment;
import com.rayllanderson.raybank.models.transaction.CardTransaction;
import com.rayllanderson.raybank.repositories.CreditCardRepository;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.services.creditcard.inputs.PaymentCardInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardPaymentService {

    private final CreditCardRepository creditCardRepository;
    private final UserRepository userRepository;

    @Transactional
    public CardTransaction pay(final PaymentCardInput paymentInput) {
        final CreditCard creditCard = creditCardRepository.findByNumber(paymentInput.getCardNumber())
                .orElseThrow(() -> new NotFoundException("Cartão de crédito inexistente"));

        validateCvvAndExpiryDate(paymentInput, creditCard);

        final var establishmentAccount = getEstablishmentAccount(paymentInput);

        if (establishmentAccount.sameCard(creditCard)) {
            throw new UnprocessableEntityException("Estabelecimento não pode receber pagamentos desse cartão");
        }

        final CardPayment payment = getCardPayment(paymentInput);

        creditCard.pay(payment);
        establishmentAccount.receiveCardPayment(paymentInput.getAmount());

        return CardTransaction.from(paymentInput, creditCard);
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
