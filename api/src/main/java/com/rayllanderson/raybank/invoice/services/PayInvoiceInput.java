package com.rayllanderson.raybank.card.services.invoice;

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
    private String userId;
    private String invoiceId;
}
