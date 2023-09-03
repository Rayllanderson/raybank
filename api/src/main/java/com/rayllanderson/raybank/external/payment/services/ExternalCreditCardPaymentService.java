package com.rayllanderson.raybank.external.payment.services;

import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.external.payment.CardUtil;
import com.rayllanderson.raybank.external.payment.repositories.ExternalTransactionRepository;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentRequest;
import com.rayllanderson.raybank.external.payment.responses.ExternalPaymentResponse;
import com.rayllanderson.raybank.repositories.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalCreditCardPaymentService implements ExternalPaymentMethod {

    private final CreditCardRepository creditCardRepository;
    private final ExternalTransactionRepository externalTransactionRepository;

    @Override
    @Transactional
    public ExternalPaymentResponse pay(ExternalPaymentRequest request) {
        var externalTransaction = request.toModel();

        var creditCard = creditCardRepository.findByNumber(CardUtil.getCardNumber(request)).orElseThrow(() -> {
            log.error("Pagamento não efetuado. Cartão de crédito {} não encontrado.", request.getNumberIdentifier());
            throw new RaybankExternalException.CreditCardNotFound("Credit Card=" + request.getNumberIdentifier() + " not found", externalTransaction);
        });

        try {
//            creditCard.pay(request.getValue());
            creditCardRepository.save(creditCard);
            externalTransactionRepository.save(externalTransaction);
            log.info("Pagamento efetuado com sucesso={}", externalTransaction);
            return ExternalPaymentResponse.fromModel(externalTransaction);
        } catch (UnprocessableEntityException e) {
            log.error("Pagamento não efetuado. Cartão de crédito {} não possui limite suficiente.", request.getNumberIdentifier());
            throw new RaybankExternalException.InsufficientCreditCardLimit("Card=" + request.getNumberIdentifier() + " has no " +
                    "available limit", externalTransaction);
        }
    }
}
