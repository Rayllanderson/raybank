package com.rayllanderson.raybank.external.payment;

import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentRequest;
import com.rayllanderson.raybank.repositories.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.CREDIT_CARD_BADLY_FORMATTED;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.CREDIT_CARD_NOT_FOUND;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.INSUFFICIENT_LIMIT;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalCreditCardPaymentService implements ExternalPaymentMethod {

    private final CreditCardRepository creditCardRepository;

    @Override
    @Transactional
    public void pay(ExternalPaymentRequest request) {

        var creditCard = creditCardRepository.findByCardNumber(getCardNumber(request)).orElseThrow(() -> {
            log.error("Pagamento não efetuado. Cartão de crédito {} não encontrado.", request.getNumberIdentifier());
            throw new RaybankExternalException(CREDIT_CARD_NOT_FOUND, "Cartão=" + request.getNumberIdentifier() + " não encontrado");
        });

        try {
            var total = request.getValue();
            creditCard.makePurchase(total);
            creditCardRepository.save(creditCard);
            log.info("Pagamento efetuado com sucesso para cartão de crédito {}", request.getNumberIdentifier());
        } catch (UnprocessableEntityException e) {
            log.error("Pagamento não efetuado. Cartão de crédito {} não possui limite suficiente.", request.getNumberIdentifier());
            throw new RaybankExternalException(INSUFFICIENT_LIMIT, "Cartão " + creditCard + " não tem limite disponível");
        }
    }

    private Long getCardNumber(ExternalPaymentRequest request) {
        try {
            return Long.valueOf(request.getNumberIdentifier());
        } catch (NumberFormatException e) {
            var message = "Cartão=" + request.getNumberIdentifier() + " está mal formatado";
            log.error("Pagamento não efetuado. {}", message);
            throw new RaybankExternalException(CREDIT_CARD_BADLY_FORMATTED, message);
        }
    }
}
