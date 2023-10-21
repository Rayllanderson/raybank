package com.rayllanderson.raybank.transaction.models;

import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.util.InvoiceCreditDescriptionUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.rayllanderson.raybank.invoice.constants.InvoiceCreditDescriptionConstant.INVOICE_PAYMENT_DESCRIPTION;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Transaction {

    @Id
    protected String id;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    protected LocalDateTime moment;

    @Column(nullable = false)
    protected BigDecimal amount;

    protected String referenceId;

    protected String description;

    @Column(nullable = false)
    protected String accountId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected TransactionType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected TransactionMethod method;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected FinancialMovement financialMovement;
    @Embedded
    protected Debit debit;

    @Embedded
    protected Credit credit;

    @PrePersist
    public void createId() {
        if (Objects.isNull(this.id)) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public static Transaction creditInvoice(BigDecimal amount, String accountId, Transaction debitTransaction) {
        final var credit = debitTransaction.getCredit();
        final var debit = debitTransaction.getDebit();
        return Transaction.builder()
                .amount(amount)
                .moment(LocalDateTime.now())
                .description(INVOICE_PAYMENT_DESCRIPTION)
                .financialMovement(FinancialMovement.CREDIT)
                .debit(debit)
                .credit(credit)
                .type(TransactionType.DEPOSIT)
                .method(debitTransaction.getMethod())
                .referenceId(debitTransaction.getId())
                .accountId(accountId)
                .build();
    }

    public static Transaction refundInvoice(BigDecimal amount, String accountId, Transaction debitTransaction) {
        final var credit = new Credit(debitTransaction.getDebit().getId(), Credit.Destination.valueOf(debitTransaction.getDebit().getOrigin().name()));
        final var debit = new Debit(debitTransaction.getCredit().getId(), Debit.Origin.valueOf(debitTransaction.getCredit().getDestination().name()));
        return Transaction.builder()
                .amount(amount)
                .moment(LocalDateTime.now())
                .description(InvoiceCreditDescriptionUtil.fromRefund(debitTransaction.getDescription()))
                .financialMovement(FinancialMovement.CREDIT)
                .type(TransactionType.REFUND)
                .debit(debit)
                .credit(credit)
                .method(debitTransaction.getMethod())
                .referenceId(debitTransaction.getId())
                .accountId(accountId)
                .build();
    }
}
