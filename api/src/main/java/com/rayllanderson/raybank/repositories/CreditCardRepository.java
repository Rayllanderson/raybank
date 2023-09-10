package com.rayllanderson.raybank.repositories;

import com.rayllanderson.raybank.models.CreditCard;
import com.rayllanderson.raybank.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
    boolean existsByNumber(Long number);
    boolean existsByBankAccountId(Long accountId);
    Optional<CreditCard> findByBankAccountId(Long accountId);
    Optional<CreditCard> findByBankAccountUserId(String userId);

    CreditCard findByInvoicesContaining(Invoice invoice);
    Optional<CreditCard> findByNumber(Long number);
}
