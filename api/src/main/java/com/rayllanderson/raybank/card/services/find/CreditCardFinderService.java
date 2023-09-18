package com.rayllanderson.raybank.card.services.find;

import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditCardFinderService {

    private final CreditCardRepository creditCardRepository;

    @Transactional(readOnly = true)
    public CardDetailsOutput findById(String cardId){
        return CardDetailsOutput.fromCreditCard(creditCardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Cartão não encontrado")));
    }
}
