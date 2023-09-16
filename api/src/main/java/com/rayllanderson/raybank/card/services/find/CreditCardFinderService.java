package com.rayllanderson.raybank.card.services.find;

import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditCardFinderService {

    private final UserRepository userRepository;
    private final CreditCardRepository creditCardRepository;

    @Transactional(readOnly = true)
    public CreditCard findByUserId(String userId){
        final var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuário não existe"));
        return user.getBankAccount().getCreditCard();
    }
}
