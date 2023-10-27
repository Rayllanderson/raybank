package com.rayllanderson.raybank.core.security.method;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.contact.gateway.ContactGateway;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.statement.gateway.BankStatementGateway;
import com.rayllanderson.raybank.statement.models.BankStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.core.security.keycloak.JwtUtils.getAccountIdFrom;

@Component
@RequiredArgsConstructor
public class MethodSecurityChecker {

    private final CardRepository cardRepository;
    private final BankStatementGateway bankStatementGateway;
    private final ContactGateway contactGateway;
    private final PixGateway pixGateway;
    private final InvoiceGateway invoiceGateway;

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

    public boolean checkContact(String contactId, Jwt jwt) {
        if (contactId == null || jwt == null) return false;

        final var accountId = getAccountIdFrom(jwt);
        if (accountId == null) return false;

        if (!contactGateway.existsByContactIdAndOwnerId(contactId, accountId))
            throw NotFoundException.formatted("Contato não encontrado");

        return true;
    }

    public boolean checkStatement(String statementId, Jwt jwt) {
        if (statementId == null || jwt == null) return false;

        final BankStatement statement = bankStatementGateway.findById(statementId);

        if (statement == null) return false;

        final var accountId = getAccountIdFrom(jwt);
        if (accountId == null) return false;

        if (statement.getAccountId().equals(accountId)) {
            return true;
        }
        throw NotFoundException.formatted("Extrato não encontrado");
    }

    public boolean checkAccountAndCard(String accountId, String cardId, Jwt jwt) {
        return this.checkAccount(accountId, jwt) && this.checkCard(cardId, jwt);
    }

    public boolean checkAccountAndContact(String accountId, String contactId, Jwt jwt) {
        return this.checkAccount(accountId, jwt) && this.checkContact(contactId, jwt);
    }

    public boolean checkPixKey(String key, Jwt jwt) {
        final var accountIdFromJwt = getAccountIdFrom(jwt);
        if (accountIdFromJwt == null) return false;

        PixKey pixKey = pixGateway.findKeyByKey(key);

        if (pixKey.sameAccount(accountIdFromJwt))
            return true;
        throw NotFoundException.formatted("Chave Pix %s não encontrada", key);
    }

    public boolean checkInvoice(String accountId, String cardId, String invoiceId, Jwt jwt) {
        return checkAccountAndCard(accountId, cardId, jwt) && checkInvoice(cardId, invoiceId);
    }

    private boolean checkInvoice(String cardId, String invoiceId) {
        final var invoice = invoiceGateway.findById(invoiceId);
        if (invoice.getCardId().equals(cardId))
            return true;
        throw NotFoundException.formatted("Fatura não encontrada");
    }
}
