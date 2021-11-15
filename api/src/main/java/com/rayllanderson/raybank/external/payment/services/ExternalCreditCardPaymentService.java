package com.rayllanderson.raybank.external.payment.services;

import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.external.payment.CardUtil;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentRequest;
import com.rayllanderson.raybank.external.payment.responses.ExternalPaymentResponse;
import com.rayllanderson.raybank.repositories.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.CREDIT_CARD_NOT_FOUND;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.INSUFFICIENT_CREDIT_CARD_LIMIT;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalCreditCardPaymentService implements ExternalPaymentMethod {

    private final CreditCardRepository creditCardRepository;

    @Override
    @Transactional
    public ExternalPaymentResponse pay(ExternalPaymentRequest request) {

        var creditCard = creditCardRepository.findByCardNumber(CardUtil.getCardNumber(request)).orElseThrow(() -> {
            log.error("Pagamento não efetuado. Cartão de crédito {} não encontrado.", request.getNumberIdentifier());
            throw new RaybankExternalException(CREDIT_CARD_NOT_FOUND, "Credit Card=" + request.getNumberIdentifier() + " not found");
        });

        try {
            var total = request.getValue();
            creditCard.makeCreditPurchase(total);
            creditCardRepository.save(creditCard);
            log.info("Pagamento efetuado com sucesso para cartão de crédito {}", request.getNumberIdentifier());
            return ExternalPaymentResponse.fromRequest(request);
        } catch (UnprocessableEntityException e) {
            log.error("Pagamento não efetuado. Cartão de crédito {} não possui limite suficiente.", request.getNumberIdentifier());
            throw new RaybankExternalException(INSUFFICIENT_CREDIT_CARD_LIMIT, "Card=" + request.getNumberIdentifier() + " has no " +
                    "available limit");
        }
    }
}
