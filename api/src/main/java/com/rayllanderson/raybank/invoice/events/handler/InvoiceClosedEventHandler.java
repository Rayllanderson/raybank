package com.rayllanderson.raybank.invoice.events.handler;

import com.rayllanderson.raybank.invoice.events.InvoiceClosedEvent;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthOf;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceClosedEventHandler {

    private final InvoiceGateway invoiceGateway;

    @Async
    @TransactionalEventListener
    @Retryable(maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public void process(final InvoiceClosedEvent event) {
        log.info("Handling InvoiceClosedEvent for invoice id {}", event.getInvoiceId());

        final var allInvoicesByCard = this.invoiceGateway.findAllByCardIdAndStatus(event.getCardId(), List.of(InvoiceStatus.NONE));
        final InvoiceListHelper invoices = new InvoiceListHelper(allInvoicesByCard);

        final Invoice nextInvoice = getNextInvoice(event.getCardId(), invoices);
        nextInvoice.open();

        final var closedInvoice = invoiceGateway.findById(event.getInvoiceId());

        if (closedInvoice.hasRemainingBalance()) {
            closedInvoice.transferRemaingBalanceTo(nextInvoice);
        }

        invoiceGateway.save(nextInvoice);
        invoiceGateway.save(closedInvoice);
    }

    private Invoice getNextInvoice(String cardId, InvoiceListHelper invoices) {
        return invoices.getCurrentOpenInvoice().orElseGet(() -> {
            final Integer dayOfDueDate = invoiceGateway.getDayOfDueDateByCardId(cardId);
            return Invoice.createOpenInvoice(plusOneMonthOf(dayOfDueDate), cardId);
        });
    }
}
