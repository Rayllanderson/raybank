package com.rayllanderson.raybank.invoice.services.scheduler;

import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloseInvoiceService {

    private final InvoiceRepository invoiceRepository;

    public void execute() {
        final var now = LocalDate.now();
        final List<Invoice> invoicesToClose = invoiceRepository.findAllByClosingDateLessThanEqualAndStatus(now, InvoiceStatus.OPEN);

        invoicesToClose.forEach(invoiceToClose -> {
            try {
                invoiceToClose.close();
                invoiceRepository.saveAndFlush(invoiceToClose);
            } catch (final Exception e) {
                log.error("Failed to close invoice {}", invoiceToClose.getId(), e);
            }
        });
    }

}
