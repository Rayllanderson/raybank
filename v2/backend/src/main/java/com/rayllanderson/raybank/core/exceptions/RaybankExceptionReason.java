package com.rayllanderson.raybank.core.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RaybankExceptionReason {

    CARD_NOT_FOUND("Card was not found", "CNF404"),
    CARD_EXPIRED("Card is expired", "CED422"),
    CARD_INACTAVED("Card is inactivated", "CNA422"),
    CARD_INVALID_EXPIRY_DATE("The expiry date is invalid", "CIE422"),
    CARD_INVALID_SECURITY_CODE("The security code is invalid", "CIS422"),
    CARD_ALREADY_REGISTERED("Card is alterady registered", "CAR422"),
    CARD_NUMBER_BADLY_FORMATTED("Card number is badly formatted", "CBF400"),
    CARD_SECURITYCODE_BADLY_FORMATTED("Card Security Code is badly formatted", "SBF400"),
    INVALID_PAYMENT_TYPE("The payment Type is not valid", "IPT400"),
    INVALID_DUE_DATE("The Due Date selected is not available", "IDD400"),
    INSUFFICIENT_CARD_LIMIT("Card has no available limit", "ICL422"),
    INSUFFICIENT_ACCOUNT_BALANCE("Client has no available balance", "IAB422"),
    ACCOUNT_NOT_FOUND("Account not found", "ANF404"),
    ACCOUNT_INACTAVED("Account is inactivated", "ANA422"),
    CONTACT_NOT_FOUND("Contact is not found", "CTF404"),
    STATAMENT_NOT_FOUND("Bank Statement is not found", "SNF404"),
    DEBIT_SAME_ACCOUNT("The debit account is equals to Credit account", "DSA422"),
    DEBIT_ORIGIN_DIFFERENT("The debit origin is different", "DOD422"),
    ESTABLISHMENT_WITH_SAME_CARD("The payment method is not valid", "ESC422"),
    ESTABLISHMENT_NOT_ACTIVE("The Establishment is not active to receive payment", "ENA422"),
    ESTABLISHMENT_INVALID("This Establishment is not able to receive any payment", "EID422"),
    INVOICE_NOT_PAYABLE("The invoice is not able to receive payments.", "INP422"),
    INVOICE_PAID("The invoice is already paid.", "IAP422"),
    INVOICE_PARTIAL_PAYMENT_NOT_AVAILABLE("Partial payment is not possible.", "IPA422"),
    BOLETO_NOT_FOUND("Boleto was not found", "BNF404"),
    BOLETO_PAID("Boleto is paid", "BPD422"),
    BOLETO_EXPIRED("Boleto is expired", "BED422"),
    BOLETO_LIQUIDATED("Boleto is liquidated", "BLD422"),
    BOLETO_NOT_LIQUIDATED("Boleto is not liquidated", "BNL422"),
    BOLETO_BENEFICIARY_TYPE_BADLY_FORMATTED("Beneficiary Type is badly formatted", "BBF400"),
    QR_CODE_EXPIRED("The QR Code is expired", "QCE422"),
    QR_CODE_NOT_FOUND("QR Code not found", "QNF404"),
    PIX_RETURN_EXCEEDED("The amount is higher than the total available", "PRE422"),
    PIX_NOT_FOUND("PIX not found", "PNF404"),
    PIX_RETURN_NOT_FOUND("PIX Return not found", "PRN404"),
    PIX_KEY_REGISTERED("The Key is already in use", "PKR422"),
    PIX_KEY_NOT_FOUND("PIX Key not found", "PKN404"),
    PIX_KEY_EXCEEDED("The number of Key reached the maximum", "PKE422"),
    PIX_KEY_TYPE_INVALID("Pix key type is not allowed or does not exist", "PTI400"),
    PIX_KEY_BADLY_FORMATTED("Pix key is not well formatted for the selected type", "PBF400"),
    PIX_LIMIT_INSUFFICIENT("The limit is not enough for transaction", "PLI422"),
    PIX_LIMIT_NOT_FOUND("PIX Limit not found", "PLN404"),
    INSTALLMENTPLAN_REFUNDED("The installment plan is already refunded", "IRD422"),
    INSTALLMENTPLAN_PARTIAL_REFUNDED("The installment plan contains a partial refund", "IPR422"),
    INSTALLMENTPLAN_NOT_FOUND("Installment plan not found", "IPN404"),
    INSTALLMENTPLAN_PARTIAL_REFUND_EXCEEDED("The amount is higher than the total available", "IRE422"),
    INVOICE_NOT_FOUND("Invoice not found", "INF404"),
    INTERNAL_SERVER_ERROR("Internal Error", "ISE500"),
    USER_NOT_FOUND("User not found", "UNF404"),
    USERNAME_TAKEN("Username is already taken", "UET422"),
    TRANSACTION_NOT_FOUND("Transaction not found", "TNF404"),
    REFUND_AMOUNT_INVALID("The refund amount is higher than transaction amount", "RAI422"),
    REFUND_AMOUNT_HIGHER("The refund amount sum is higher than amount refunded", "RAH422");

    private final String description;
    private final String code;
}
