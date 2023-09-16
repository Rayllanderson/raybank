package com.rayllanderson.raybank.invoice.repository;

import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findAllByClosingDateLessThanEqualAndStatus(LocalDate closingDate, InvoiceStatus status);
    Set<Invoice> findAllByCreditCardId(String creditCardId);
    List<Invoice> findAllByDueDateLessThanEqualAndStatusAndTotalIsNot(LocalDate dueDate, InvoiceStatus status, BigDecimal total);
}
