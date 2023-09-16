package com.rayllanderson.raybank.card.services.invoice.scheduler;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.card.models.Invoice;
import com.rayllanderson.raybank.card.models.InvoiceStatus;
import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CloseInvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CreditCardRepository cardRepository;

    public void execute() {
        final var now = LocalDate.now();
        final List<Invoice> invoicesToClose = invoiceRepository.findAllByClosingDateLessThanEqualAndStatus(now, InvoiceStatus.OPEN);

        invoicesToClose.forEach(closedInvoice -> {
            closedInvoice.close();

            final CreditCard creditCard = cardRepository.findByInvoicesContaining(closedInvoice);

            final Invoice nextInvoice = creditCard.getCurrentOpenInvoice();
            nextInvoice.open();

            invoiceRepository.saveAllAndFlush(List.of(closedInvoice, nextInvoice));
        });
    }

}
