package com.rayllanderson.raybank.services.creditcard.inputs;

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
