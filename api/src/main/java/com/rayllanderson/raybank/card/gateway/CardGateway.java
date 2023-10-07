package com.rayllanderson.raybank.card.gateway;

import com.rayllanderson.raybank.card.models.Card;

public interface CardGateway {
    Card findById(final String id);
}
