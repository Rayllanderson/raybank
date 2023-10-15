package com.rayllanderson.raybank.invoice.services.scheduler;

import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthOf;

@Slf4j
@Service
@RequiredArgsConstructor
class InvoiceClosedService {

    private final InvoiceGateway invoiceGateway;

    //todo:: retry

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void process(final Invoice closedInvoice) {
        final var allInvoicesByCard = this.invoiceGateway.findAllByCardIdAndStatus(closedInvoice.getCardId(), List.of(InvoiceStatus.NONE));
        final InvoiceListHelper invoices = new InvoiceListHelper(allInvoicesByCard);

        final Invoice nextInvoice = getNextInvoice(closedInvoice, invoices);
        nextInvoice.open();

        if (closedInvoice.hasRemainingBalance()) {
            closedInvoice.transferRemaingBalanceTo(nextInvoice);
        }

        invoiceGateway.save(nextInvoice);
        invoiceGateway.save(closedInvoice);
    }

    private Invoice getNextInvoice(Invoice closedInvoice, InvoiceListHelper invoices) {
        return invoices.getCurrentOpenInvoice().orElseGet(() -> {
            final Integer dayOfDueDate = invoiceGateway.getDayOfDueDateByCardId(closedInvoice.getCardId());
            return Invoice.createOpenInvoice(plusOneMonthOf(dayOfDueDate), closedInvoice.getCardId());
        });
    }
}
