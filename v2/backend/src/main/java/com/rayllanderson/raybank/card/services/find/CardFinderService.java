package com.rayllanderson.raybank.card.services.find;

import com.rayllanderson.raybank.card.gateway.CardGateway;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.limit.FindCardLimitService;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CardFinderService {

    private final CardGateway cardGateway;
    private final FindCardLimitService cardLimitService;
    private final InvoiceGateway invoiceGateway;

    @Transactional(readOnly = true)
    public CardDetailsOutput findById(String cardId){
        final Card card = cardGateway.findById(cardId);

        return getCardDetailsOutput(cardId, card);
    }

    private CardDetailsOutput getCardDetailsOutput(String cardId, Card card) {
        final var usedLimit = cardLimitService.findUsedLimit(cardId);
        final var availableLimit = card.getLimit().subtract(usedLimit);

        final var invoiceTotal = getInvoiceValue(cardId);

        return CardDetailsOutput.fromCreditCard(card, usedLimit, availableLimit, invoiceTotal);
    }

    @Transactional(readOnly = true)
    public CardDetailsOutput findByAccountId(String accountId){
        final Card card = cardGateway.findByAccountId(accountId);

        return getCardDetailsOutput(card.getId(), card);
    }

    private BigDecimal getInvoiceValue(final String cardId) {
        try {
            return invoiceGateway.findCurrentByCardId(cardId).getTotal();
        } catch (Exception e) {
            return null;
        }
    }

}
