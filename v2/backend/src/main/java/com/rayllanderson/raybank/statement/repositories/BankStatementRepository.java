package com.rayllanderson.raybank.statement.repositories;

import com.rayllanderson.raybank.statement.models.BankStatement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BankStatementRepository extends JpaRepository<BankStatement, String> {
    Optional<BankStatement> findByIdAndAccountId(String id, String accountId);
    List<BankStatement> findAllByAccountId(String accountId);
    Page<BankStatement> findAllByAccountId(String accountId, Pageable pageable);
    Page<BankStatement> findAllByAccountIdAndMethodIn(String accountId, List<String> methods, Pageable pageable);
    Page<BankStatement> findAllByAccountIdAndMethodNotIn(String accountId, List<String> methods, Pageable pageable);
    Page<BankStatement> findAllByAccountIdAndCreditDestinationAndType(String accountId, String destination, String type, Pageable pageable);

    @Query("""
           SELECT bs FROM BankStatement bs
           WHERE bs.accountId = :accountId
           AND (lower(cast(bs.amount as string)) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.description) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.credit.name) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.debit.name) LIKE lower(concat('%', :searchTerm, '%')))
           """)
    Page<BankStatement> searchByTermAndAccountId(String searchTerm, String accountId, Pageable pageable);

    @Query("""
           SELECT bs FROM BankStatement bs
           WHERE bs.accountId = :accountId
           AND (lower(cast(bs.amount as string)) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.description) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.credit.name) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.debit.name) LIKE lower(concat('%', :searchTerm, '%')))
           AND bs.method IN :methods
           """)
    Page<BankStatement> searchByTermAndAccountIdAndMethodIn(String searchTerm, String accountId, List<String> methods, Pageable pageable);

    @Query("""
           SELECT bs FROM BankStatement bs
           WHERE bs.accountId = :accountId
           AND (lower(cast(bs.amount as string)) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.description) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.credit.name) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.debit.name) LIKE lower(concat('%', :searchTerm, '%')))
           AND bs.method NOT IN :methods
           """)
    Page<BankStatement> searchByTermAndAccountIdAndMethodNotIn(String searchTerm, String accountId, List<String> methods, Pageable pageable);

    @Query("""
           SELECT bs FROM BankStatement bs
           WHERE bs.accountId = :accountId
           AND (lower(cast(bs.amount as string)) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.description) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.credit.name) LIKE lower(concat('%', :searchTerm, '%'))
           OR lower(bs.debit.name) LIKE lower(concat('%', :searchTerm, '%')))
           AND lower(bs.credit.destination) LIKE lower(concat('%', :destination, '%'))
           AND lower(bs.type) LIKE lower(concat('%', :type, '%'))
           """)
    Page<BankStatement> searchByTermAndAccountIdAndCreditDestinationAndType(String searchTerm, String accountId, String destination, String type, Pageable pageable);
}
