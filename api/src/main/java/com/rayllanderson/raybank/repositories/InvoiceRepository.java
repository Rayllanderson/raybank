package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.Invoice;
import com.rayllanderson.raybank.models.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findAllByClosingDateLessThanEqualAndStatus(LocalDate closingDate, InvoiceStatus status);
    List<Invoice> findAllByDueDateAndStatus(LocalDate dueDate, InvoiceStatus status);
}
