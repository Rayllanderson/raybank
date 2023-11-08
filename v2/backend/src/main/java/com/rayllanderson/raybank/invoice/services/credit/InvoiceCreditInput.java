package com.rayllanderson.raybank.invoice.services.credit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreditInput {
    private BigDecimal amount;
    private String accountId;
    private String cardId;
    private String invoiceId;
}
