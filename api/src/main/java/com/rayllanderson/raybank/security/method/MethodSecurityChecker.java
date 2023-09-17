package com.rayllanderson.raybank.security.method;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.statement.repository.BankStatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getUserIdFrom;

@Component
@RequiredArgsConstructor
public class MethodSecurityChecker {

    private final CreditCardRepository creditCardRepository;
    private final BankStatementRepository bankStatementRepository;

    public boolean checkCard(String cardId, Jwt jwt) {
        if (cardId == null || jwt == null) return false;

        final var userId = getUserIdFrom(jwt);
        if (userId == null) return false;

        final CreditCard authenticatedUserCard = creditCardRepository.findByBankAccountUserId(userId).orElse(null);

        if (authenticatedUserCard == null)
            return false;

        return authenticatedUserCard.getId().equals(cardId);
    }

    public boolean checkStatement(String statementId, Jwt jwt) {
        if (statementId == null || jwt == null) return false;

        final BankStatement statement = bankStatementRepository.findById(statementId).orElse(null);

        if (statement == null) return false;

        final var userId = getUserIdFrom(jwt);
        if (userId == null) return false;

        return statement.getUserId().equals(userId);
    }
}
