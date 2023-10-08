package com.rayllanderson.raybank.external.payment.services;

import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.external.payment.CardUtil;
import com.rayllanderson.raybank.external.payment.repositories.ExternalTransactionRepository;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentRequest;
import com.rayllanderson.raybank.external.payment.responses.ExternalPaymentResponse;
import com.rayllanderson.raybank.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalCreditCardPaymentService implements ExternalPaymentMethod {

    private final CardRepository cardRepository;
    private final ExternalTransactionRepository externalTransactionRepository;

    @Override
    @Transactional
    public ExternalPaymentResponse pay(ExternalPaymentRequest request) {
        var ExternalTransaction = request.toModel();

        var creditCard = cardRepository.findByNumber(CardUtil.getCardNumber(request)).orElseThrow(() -> {
            log.error("Pagamento não efetuado. Cartão de crédito {} não encontrado.", request.getNumberIdentifier());
            throw new RaybankExternalException.CreditCardNotFound("Credit Card=" + request.getNumberIdentifier() + " not found", ExternalTransaction);
        });

        try {
//            creditCard.debit(request.getValue());
            cardRepository.save(creditCard);
            externalTransactionRepository.save(ExternalTransaction);
            log.info("Pagamento efetuado com sucesso={}", ExternalTransaction);
            return ExternalPaymentResponse.fromModel(ExternalTransaction);
        } catch (UnprocessableEntityException e) {
            log.error("Pagamento não efetuado. Cartão de crédito {} não possui limite suficiente.", request.getNumberIdentifier());
            throw new RaybankExternalException.InsufficientCreditCardLimit("Card=" + request.getNumberIdentifier() + " has no " +
                    "available limit", ExternalTransaction);
        }
    }
}
