package com.rayllanderson.raybank.card.repository;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.invoice.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
    boolean existsByNumber(Long number);
    boolean existsByBankAccountId(Long accountId);
    Optional<CreditCard> findByBankAccountId(Long accountId);
    Optional<CreditCard> findByBankAccountUserId(String userId);
    CreditCard findByInvoicesContaining(Invoice invoice);
    Optional<CreditCard> findByNumber(Long number);
    Integer findDayOfDueDateById(String id);
}
