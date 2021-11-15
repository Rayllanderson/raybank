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

import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.DEBIT_CARD_NOT_FOUND;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.INSUFFICIENT_ACCOUNT_BALANCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalDebitCardPaymentService implements ExternalPaymentMethod {

    private final CreditCardRepository cardRepository;

    @Override
    @Transactional
    public ExternalPaymentResponse pay(ExternalPaymentRequest request) {
        var debitCard = cardRepository.findByCardNumber(CardUtil.getCardNumber(request)).orElseThrow(() -> {
            log.error("Pagamento não efetuado. Cartão de débito {} não encontrado.", request.getNumberIdentifier());
            throw new RaybankExternalException(DEBIT_CARD_NOT_FOUND, "Debit card=" + request.getNumberIdentifier() + " not found");
        });

        var total = request.getValue();
        try {
            debitCard.makeDebitPurchase(total);
            cardRepository.save(debitCard);
            log.info("Pagamento efetuado com sucesso no cartão de débito={}, valor={}", request.getNumberIdentifier(), total);
            return ExternalPaymentResponse.fromRequest(request);
        } catch (UnprocessableEntityException e) {
            log.error("Pagamento não efetuado no cartão de débito={}. Cliente não possui saldo={} disponível na conta",
                    request.getNumberIdentifier(), total);
            throw new RaybankExternalException(INSUFFICIENT_ACCOUNT_BALANCE);
        }
    }
}
