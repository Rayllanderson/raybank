package com.rayllanderson.raybank.invoice.models.inputs;

import com.rayllanderson.raybank.invoice.models.InvoiceCreditType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInvoiceCredit {
    private BigDecimal amount;
    private InvoiceCreditType type;
    private String description;
    private String transactionId;
    private LocalDateTime occuredOn;
}
