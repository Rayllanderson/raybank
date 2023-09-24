package com.rayllanderson.raybank.invoice.repository;

import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findAllByClosingDateLessThanEqualAndStatus(LocalDate closingDate, InvoiceStatus status);
    List<Invoice> findAllByCard_Id(String cardId);
    Optional<Invoice> findAnyByCard_Id(String cardId);
    List<Invoice> findAllByDueDateLessThanEqualAndStatusAndTotalIsNot(LocalDate dueDate, InvoiceStatus status, BigDecimal total);
}
