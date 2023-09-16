package com.rayllanderson.raybank.card.services.invoice.scheduler;

import com.rayllanderson.raybank.card.models.Invoice;
import com.rayllanderson.raybank.card.models.InvoiceStatus;
import com.rayllanderson.raybank.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OverdueInvoiceService {

    private final InvoiceRepository invoiceRepository;

    public void execute() {
        final var now = LocalDate.now();
        final List<Invoice> invoicesToClose = invoiceRepository.findAllByDueDateLessThanEqualAndStatusAndTotalIsNot(now,
                InvoiceStatus.CLOSED,
                BigDecimal.ZERO.setScale(2));

        invoicesToClose.forEach(Invoice::overdue);

        invoiceRepository.saveAll(invoicesToClose);
    }

}
