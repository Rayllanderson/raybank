package com.rayllanderson.raybank.services.creditcard;

import com.rayllanderson.raybank.models.Invoice;
import com.rayllanderson.raybank.models.InvoiceStatus;
import com.rayllanderson.raybank.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OverdueInvoiceService {

    private final InvoiceRepository invoiceRepository;

    public void execute() {
        final var now = LocalDate.now();
        final List<Invoice> invoicesToClose = invoiceRepository.findAllByDueDateLessThanEqualAndStatusAndTotalNotLike(now,
                InvoiceStatus.CLOSED,
                BigDecimal.ZERO.setScale(2));

        invoicesToClose.forEach(Invoice::overdue);

        invoiceRepository.saveAll(invoicesToClose);
    }

}
