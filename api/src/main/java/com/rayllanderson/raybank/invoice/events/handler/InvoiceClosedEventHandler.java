package com.rayllanderson.raybank.invoice.events.handler;

import com.rayllanderson.raybank.invoice.events.InvoiceClosedEvent;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthOf;

@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class InvoiceClosedEventHandler {

    private final InvoiceGateway invoiceGateway;

    @Async
    @TransactionalEventListener
    public void handler(final InvoiceClosedEvent event) {
        final String invoiceId = event.getInvoiceId();
        log.info("Handling InvoiceClosedEvent for invoice id {}", invoiceId);

        final var allInvoicesByCard = this.invoiceGateway.findAllByCardId(event.getCardId());
        final InvoiceListHelper invoices = new InvoiceListHelper(allInvoicesByCard);

        Invoice invoice = getNextInvoice(event, invoices);
        invoice.open();

        invoiceGateway.save(invoice);
    }

    private Invoice getNextInvoice(InvoiceClosedEvent event, InvoiceListHelper invoices) {
        return invoices.getCurrentOpenInvoice().orElseGet(() -> {
            final Integer dayOfDueDate = invoiceGateway.getDayOfDueDateByCardId(event.getCardId());
            return Invoice.createOpenInvoice(plusOneMonthOf(dayOfDueDate), event.getCardId());
        });
    }
}
