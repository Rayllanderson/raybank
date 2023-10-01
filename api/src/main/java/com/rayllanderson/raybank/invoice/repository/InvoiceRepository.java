package com.rayllanderson.raybank.invoice.repository;

import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    List<Invoice> findAllByClosingDateLessThanEqualAndStatus(LocalDate closingDate, InvoiceStatus status);
    List<Invoice> findAllByCard_Id(String cardId);
    List<Invoice> findAllByCard_IdAndStatusIn(String cardId, List<InvoiceStatus> status);
    Optional<Invoice> findAnyByCard_Id(String cardId);
    List<Invoice> findAllByDueDateLessThanEqualAndStatusAndTotalIsNot(LocalDate dueDate, InvoiceStatus status, BigDecimal total);

    @Query("SELECT new com.rayllanderson.raybank.invoice.repository.InvoiceWithoutInstallmentsProjection(s.id, s.dueDate, s.originalDueDate, s.closingDate, s.total, s.status, s.card) FROM Invoice s WHERE s.card.id = ?1")
    List<InvoiceWithoutInstallmentsProjection> findAllByCardIdAndWithoutInstallments(final String cardId);
}
