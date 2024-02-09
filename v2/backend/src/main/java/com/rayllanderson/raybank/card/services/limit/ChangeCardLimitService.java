package com.rayllanderson.raybank.card.services.limit;

import com.rayllanderson.raybank.card.gateway.CardGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeCardLimitService {

    private final CardGateway cardGateway;

    @Transactional
    public void change(final ChangeCardLimitInput input) {
        final var card = cardGateway.findByAccountId(input.getAccountId());

        card.changeLimit(input.getNewLimit());

        cardGateway.save(card);
    }

}
