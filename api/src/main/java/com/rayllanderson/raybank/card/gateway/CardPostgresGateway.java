package com.rayllanderson.raybank.card.gateway;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardPostgresGateway implements CardGateway {

    private final CardRepository cardRepository;

    @Override
    public void save(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card findById(String id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> NotFoundException.formatted("Cartão não encontrado"));
    }
}
