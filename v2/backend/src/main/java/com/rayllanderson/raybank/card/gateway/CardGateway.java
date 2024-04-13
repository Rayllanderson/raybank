package com.rayllanderson.raybank.card.gateway;

import com.rayllanderson.raybank.card.models.Card;

public interface CardGateway {
    Card save(Card card);
    Card findById(final String id);

    Card findByAccountId(final String id);

    Card findByNumber(Long cardNumber);

    boolean existsByBankAccountId(String id);

    boolean existsByNumber(long number);
}
