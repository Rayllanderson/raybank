package com.rayllanderson.raybank.security.method;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.statement.repository.BankStatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getAccountIdFrom;
import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getUserIdFrom;

@Component
@RequiredArgsConstructor
public class MethodSecurityChecker {

    private final CardRepository cardRepository;
    private final BankStatementRepository bankStatementRepository;

    public boolean checkAccount(String accountId, Jwt jwt) {
        if (accountId == null || jwt == null) return false;

        final var accountIdFromJwt = getAccountIdFrom(jwt);
        if (accountIdFromJwt == null) return false;

        return accountIdFromJwt.equals(accountId);
    }

    public boolean checkCard(String cardId, Jwt jwt) {
        if (cardId == null || jwt == null) return false;

        final var accountId = getAccountIdFrom(jwt);
        if (accountId == null) return false;

        final Card authenticatedUserCard = cardRepository.findByBankAccountId(accountId).orElse(null);

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

    public boolean checkAccountAndCard(String accountId, String cardId, Jwt jwt) {
        return this.checkAccount(accountId, jwt) && this.checkCard(cardId, jwt);
    }
}
