package com.rayllanderson.raybank.card.services.find;

import com.rayllanderson.raybank.card.gateway.CardGateway;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.limit.FindCardLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardFinderService {

    private final CardGateway cardGateway;
    private final FindCardLimitService cardLimitService;

    @Transactional(readOnly = true)
    public CardDetailsOutput findById(String cardId){
        final Card card = cardGateway.findById(cardId);

        final var usedLimit = cardLimitService.findUsedLimit(cardId);
        final var availableLimit = card.getLimit().subtract(usedLimit);

        return CardDetailsOutput.fromCreditCard(card, usedLimit, availableLimit);
    }
}
