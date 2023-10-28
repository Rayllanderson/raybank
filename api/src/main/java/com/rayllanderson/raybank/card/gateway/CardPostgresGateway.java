package com.rayllanderson.raybank.card.gateway;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.CARD_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class CardPostgresGateway implements CardGateway {

    private final CardRepository cardRepository;

    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card findById(String id) {
        return cardRepository.findById(id).orElseThrow(notFoundException());
    }

    @Override
    public Card findByNumber(Long cardNumber) {
        return cardRepository.findByNumber(cardNumber).orElseThrow(notFoundException());
    }

    @Override
    public boolean existsByBankAccountId(String id) {
        return cardRepository.existsByBankAccountId(id);
    }

    @Override
    public boolean existsByNumber(long number) {
        return cardRepository.existsByNumber(number);
    }

    private static Supplier<NotFoundException> notFoundException() {
        return () -> NotFoundException.with(CARD_NOT_FOUND, "Cartão não encontrado");
    }
}
