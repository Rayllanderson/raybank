package com.rayllanderson.raybank.external.exceptions;

import com.rayllanderson.raybank.external.payment.models.ExternalTransaction;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.*;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.CARD_BADLY_FORMATTED;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.CREDIT_CARD_NOT_FOUND;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.DEBIT_CARD_NOT_FOUND;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.INSUFFICIENT_ACCOUNT_BALANCE;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.INSUFFICIENT_CREDIT_CARD_LIMIT;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.INVALID_PAYMENT_METHOD;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.TOKEN_ALREADY_REGISTERED;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.TOKEN_INVALID;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.TOKEN_UNREGISTERED;
import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.bankStatement_NOT_FOUND;

@ToString
public class RaybankExternalException extends RuntimeException {
    private final RaybankExternalTypeError reason;
    private String message;
    private ExternalTransaction bankStatement;

    public RaybankExternalException(RaybankExternalTypeError reason) {
        super(reason.getDescription());
        this.reason = reason;
    }

    public RaybankExternalException(RaybankExternalTypeError reason, String message) {
        super(reason.getDescription());
        this.reason = reason;
        this.message = message;
    }

    public RaybankExternalException(RaybankExternalTypeError reason, String message, ExternalTransaction bankStatement) {
        super(reason.getDescription());
        this.reason = reason;
        this.message = message;
        this.bankStatement = bankStatement;
    }

    public RaybankExternalException(RaybankExternalTypeError reason, ExternalTransaction bankStatement) {
        super(reason.getDescription());
        this.reason = reason;
        this.bankStatement = bankStatement;
    }

    public HttpStatus getStatus() {
        return this.reason.getStatus();
    }

    public String getRayBankCode() {
        return this.reason.getRayBankCode();
    }

    public String getDescription() {
        return this.reason.getDescription();
    }

    public String getReasonValue() {
        return reason.name();
    }

    public Optional<ExternalTransaction> getBankStatement() {
        return Optional.ofNullable(bankStatement);
    }

    public static class InsufficientCreditCardLimit extends RaybankExternalException {
        public InsufficientCreditCardLimit(String message, ExternalTransaction bankStatement) {
            super(INSUFFICIENT_CREDIT_CARD_LIMIT, message, bankStatement);
        }
    }

    public static class CreditCardNotFound extends RaybankExternalException {
        public CreditCardNotFound(String message, ExternalTransaction bankStatement) {
            super(CREDIT_CARD_NOT_FOUND, message, bankStatement);
        }
    }

    public static class DebitCardNotFound extends RaybankExternalException {
        public DebitCardNotFound(String message, ExternalTransaction bankStatement) {
            super(DEBIT_CARD_NOT_FOUND, message, bankStatement);
        }
    }

    public static class CardBadlyFormatted extends RaybankExternalException {
        public CardBadlyFormatted(String message, ExternalTransaction bankStatement) {
            super(CARD_BADLY_FORMATTED, message, bankStatement);
        }
    }

    public static class InsufficientAccountBalance extends RaybankExternalException {
        public InsufficientAccountBalance() {
            super(INSUFFICIENT_ACCOUNT_BALANCE);
        }

        public InsufficientAccountBalance(String message) {
            super(INSUFFICIENT_ACCOUNT_BALANCE);
        }

        public InsufficientAccountBalance(ExternalTransaction bankStatement) {
            super(INSUFFICIENT_ACCOUNT_BALANCE, bankStatement);
        }
    }

    public static class InvalidPaymentMethod extends RaybankExternalException {
        public InvalidPaymentMethod(String message) {
            super(INVALID_PAYMENT_METHOD, message);
        }
    }

    public static class TokenAlreadyRegistered extends RaybankExternalException {
        public TokenAlreadyRegistered(String message) {
            super(TOKEN_ALREADY_REGISTERED, message);
        }
    }

    public static class TokenUnregistered extends RaybankExternalException {
        public TokenUnregistered() {
            super(TOKEN_UNREGISTERED);
        }
        public TokenUnregistered(String message) {
            super(TOKEN_UNREGISTERED, message);
        }
    }

    public static class TokenInvalid extends RaybankExternalException {
        public TokenInvalid(String message) {
            super(TOKEN_INVALID, message);
        }
    }

    public static class BankStatementNotFound extends RaybankExternalException {
        public BankStatementNotFound(String s) {
            super(bankStatement_NOT_FOUND, s);
        }
    }

    public static class AccountNotFound extends RaybankExternalException {
        public AccountNotFound(String s) {
            super(ACCOUNT_NOT_FOUND, s);
        }
    }

    public static class BoletoNotFound extends RaybankExternalException {
        public BoletoNotFound(String s) {
            super(BOLETO_NOT_FOUND, s);
        }
    }

    @Override
    public String getMessage() {
        return (this.message == null || this.message.isEmpty()) ? super.getMessage() : this.message;
    }
}
