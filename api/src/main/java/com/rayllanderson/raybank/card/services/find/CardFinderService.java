package com.rayllanderson.raybank.card.services.find;

import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditCardFinderService {

    private final CardRepository cardRepository;

    @Transactional(readOnly = true)
    public CardDetailsOutput findById(String cardId){
        return CardDetailsOutput.fromCreditCard(cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Cartão não encontrado")));
    }
}
