package com.rayllanderson.raybank.core.security.method;

import com.rayllanderson.raybank.card.gateway.CardGateway;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.contact.gateway.ContactGateway;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.installment.repository.InstallmentPlanGateway;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.pix.controllers.pixreturn.PixReturnRequest;
import com.rayllanderson.raybank.pix.controllers.qrcode.generate.GenerateQrCodeRequest;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.statement.gateway.BankStatementGateway;
import com.rayllanderson.raybank.statement.models.BankStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.CARD_NOT_FOUND;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.CONTACT_NOT_FOUND;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INSTALLMENTPLAN_NOT_FOUND;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INVOICE_NOT_FOUND;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.PIX_KEY_NOT_FOUND;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.STATAMENT_NOT_FOUND;
import static com.rayllanderson.raybank.core.security.keycloak.JwtUtils.getAccountIdFrom;

@Component
@RequiredArgsConstructor
public class MethodSecurityChecker {

    private final CardGateway cardGateway;
    private final BankStatementGateway bankStatementGateway;
    private final ContactGateway contactGateway;
    private final PixGateway pixGateway;
    private final InvoiceGateway invoiceGateway;
    private final InstallmentPlanGateway planGateway;

    public boolean checkAccount(String accountId, Jwt jwt) {
        if (accountId == null || jwt == null) return false;

        final var accountIdFromJwt = getAccountIdFrom(jwt);
        if (accountIdFromJwt == null) return false;

        return accountIdFromJwt.equals(accountId);
    }

    public boolean checkCard(String cardId, Jwt jwt) {
        final var accountId = getAccountIdFrom(jwt);

        if (cardId == null || accountId == null) return false;

        final Card authenticatedUserCard = cardGateway.findById(cardId);

        if (authenticatedUserCard.getAccountId().equals(accountId))
            return true;
        throw NotFoundException.withFormatted(CARD_NOT_FOUND, "Cartão não encontrado");
    }

    public boolean checkContact(String contactId, Jwt jwt) {
        if (contactId == null || jwt == null) return false;

        final var accountId = getAccountIdFrom(jwt);
        if (accountId == null) return false;

        if (!contactGateway.existsByContactIdAndOwnerId(contactId, accountId))
            throw NotFoundException.withFormatted(CONTACT_NOT_FOUND, "Contato não encontrado");

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
        throw NotFoundException.withFormatted(STATAMENT_NOT_FOUND, "Extrato não encontrado");
    }

    public boolean checkAccountAndCard(String accountId, String cardId, Jwt jwt) {
        return this.checkAccount(accountId, jwt) && this.checkCard(cardId, jwt);
    }

    public boolean checkAccountAndContact(String accountId, String contactId, Jwt jwt) {
        return this.checkAccount(accountId, jwt) && this.checkContact(contactId, jwt);
    }

    public boolean checkPixKey(String key, Jwt jwt) {
        if(key == null) return false;
        final var accountIdFromJwt = getAccountIdFrom(jwt);
        if (accountIdFromJwt == null) return false;

        PixKey pixKey = pixGateway.findKeyByKey(key);

        if (pixKey.sameAccount(accountIdFromJwt))
            return true;
        throw NotFoundException.withFormatted(PIX_KEY_NOT_FOUND, "Chave Pix %s não encontrada", key);
    }

    public boolean checkPixKey(GenerateQrCodeRequest request, Jwt jwt) {
        if (request == null) return false;
        return checkPixKey(request.getCreditKey(), jwt);
    }

    public boolean checkInvoice(String accountId, String invoiceId, Jwt jwt) {
        return checkAccount(accountId, jwt) && checkInvoice(accountId, invoiceId);
    }

    private boolean checkInvoice(String accountId, String invoiceId) {
        final var invoice = invoiceGateway.findById(invoiceId);
        if (invoice.getCard().getAccountId().equals(accountId))
            return true;
        throw NotFoundException.withFormatted(INVOICE_NOT_FOUND, "Fatura não encontrada");
    }

    public boolean checkPlan(String planId, Jwt jwt) {
        final var accountId = getAccountIdFrom(jwt);

        if (planId == null || accountId == null) return false;

        final var plan = planGateway.findById(planId);

        if (accountId.equals(plan.getAccountId()))
            return true;
        throw NotFoundException.withFormatted(INSTALLMENTPLAN_NOT_FOUND, "Parcelamento %s não encontrado", planId);
    }

    public boolean checkPixReturn(PixReturnRequest request, Jwt jwt) {
        final var accountId = getAccountIdFrom(jwt);

        if (request == null || accountId == null) return false;

        try {
            Pix pix = pixGateway.findPixById(request.getPixId());
            return pix.getCredit().sameAccount(accountId);
        } catch (final NotFoundException e) {
            return false;
        }
    }
}
