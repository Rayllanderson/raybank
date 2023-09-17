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
    public CardDetailsOutput findByUserId(String userId){
        return CardDetailsOutput.fromCreditCard(creditCardRepository.findByBankAccountUserId(userId)
                .orElseThrow(() -> new NotFoundException("Cartão não encontrado")));
    }
}
