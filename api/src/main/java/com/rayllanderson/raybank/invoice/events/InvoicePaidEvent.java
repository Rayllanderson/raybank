package com.rayllanderson.raybank.invoice.events;

import com.rayllanderson.raybank.event.Event;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class InvoicePaidEvent implements Event {

    private final String invoiceId;
    private final String transactionId;
    private final BigDecimal amount;
    private final BigDecimal currentTotal;
    private final InvoiceStatus status;
    private final LocalDateTime ocurredOn;

    public InvoicePaidEvent(Invoice invoice, Transaction transaction) {
        this.invoiceId = invoice.getId();
        this.transactionId = transaction.getId();
        this.amount = transaction.getAmount();
        this.currentTotal = invoice.getTotal();
        this.status = invoice.getStatus();
        this.ocurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime ocurredOn() {
        return ocurredOn;
    }
}
