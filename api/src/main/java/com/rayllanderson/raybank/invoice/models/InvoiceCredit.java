package com.rayllanderson.raybank.invoice.models;

import com.rayllanderson.raybank.invoice.models.inputs.ProcessInvoiceCredit;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceCredit {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private InvoiceCreditType type;
    private BigDecimal amount;
    private String description;
    private String transactionId;
    private LocalDateTime occuredOn;
    @ManyToOne
    private Invoice invoice;

    public static InvoiceCredit from(final ProcessInvoiceCredit credit, final Invoice invoice) {
        return new InvoiceCredit(UUID.randomUUID().toString(),
                credit.getType(),
                credit.getAmount(),
                credit.getDescription(),
                credit.getTransactionId(),
                credit.getOccuredOn(),
                invoice);
    }

    public boolean isRefund() {
        return InvoiceCreditType.REFUND.equals(this.type);
    }

    public boolean isPayment() {
        return InvoiceCreditType.INVOICE_PAYMENT.equals(this.type);
    }
}
