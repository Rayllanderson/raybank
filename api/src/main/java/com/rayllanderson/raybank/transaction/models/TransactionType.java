package com.rayllanderson.raybank.transaction.models;

public enum TransactionType {
    TRANSFER, DEPOSIT, PAYMENT, REFUND, RETURN,
    BRAZILIAN_BOLETO, BRAZILIAN_BOLETO_REFUND,
    CREDIT_CARD_PAYMENT, DEBIT_CARD_PAYMENT, CARD_RECEIVE_PAYMENT, CREDITING_CARD, CREDIT_CARD_REFUND,
    INVOICE_PAYMENT , INVOICE_PAYMENT_RECEIPT
}
