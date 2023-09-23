package com.rayllanderson.raybank.invoice.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PayInvoiceInput {
    private BigDecimal amount;
    private String accountId;
    private String cardId;
    private String invoiceId;
}
