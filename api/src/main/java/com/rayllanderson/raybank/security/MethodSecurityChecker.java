package com.rayllanderson.raybank.security;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getUserIdFrom;

@Component
@RequiredArgsConstructor
public class MethodSecurityChecker {

    private final CreditCardRepository creditCardRepository;

    public boolean checkCard(String cardId, Jwt jwt) {
        final CreditCard authenticatedUserCard = creditCardRepository.findByBankAccountUserId(getUserIdFrom(jwt))
                .orElseThrow(() -> new NotFoundException("Card not found"));

        return authenticatedUserCard.getId().equals(cardId);
    }
}
