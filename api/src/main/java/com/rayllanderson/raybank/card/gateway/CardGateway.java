package com.rayllanderson.raybank.card.gateway;

import com.rayllanderson.raybank.card.models.Card;

public interface CardGateway {
    void save(Card card);
    Card findById(final String id);
}
