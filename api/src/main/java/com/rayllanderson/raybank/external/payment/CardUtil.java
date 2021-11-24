package com.rayllanderson.raybank.external.payment;

import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CardUtil {

    /**
     * @throws RaybankExternalException CREDIT_CARD_BADLY_FORMATTED
     */
    public static Long getCardNumber(ExternalPaymentRequest request) throws RaybankExternalException {
        try {
            return Long.valueOf(request.getNumberIdentifier());
        } catch (NumberFormatException e) {
            var message = "Card=" + request.getNumberIdentifier() + " is badly formatted";
            log.error("Pagamento não efetuado. {}", message);
            throw new RaybankExternalException.CardBadlyFormatted(message, request.toModel());
        }
    }
}
