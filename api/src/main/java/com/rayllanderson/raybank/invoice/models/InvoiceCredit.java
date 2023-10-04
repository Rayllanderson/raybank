package com.rayllanderson.raybank.invoice.models;

import com.rayllanderson.raybank.invoice.models.inputs.ProcessInvoiceCredit;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceCredit {
    @Id
    private String id;
    private InvoiceCreditType type;
    private BigDecimal amount;
    private String transactionId;
    private LocalDate occuredOn;
    @ManyToOne
    private Invoice invoice;

    public static InvoiceCredit from(final ProcessInvoiceCredit credit, final Invoice invoice) {
        return new InvoiceCredit(UUID.randomUUID().toString(),
                credit.getType(),
                credit.getAmount(),
                credit.getTransactionId(),
                credit.getOccuredOn(),
                invoice);
    }

    public boolean isRefund() {
        return InvoiceCreditType.REFUND.equals(this.type);
    }
}
