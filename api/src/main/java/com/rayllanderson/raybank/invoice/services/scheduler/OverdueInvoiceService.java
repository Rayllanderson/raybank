package com.rayllanderson.raybank.invoice.services.scheduler;

import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
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
