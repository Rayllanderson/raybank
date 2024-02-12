package com.rayllanderson.raybank.transaction.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rayllanderson.raybank.bankaccount.services.credit.CreditAccountInput;
import com.rayllanderson.raybank.bankaccount.services.debit.DebitAccountInput;
import com.rayllanderson.raybank.bankaccount.services.deposit.DepositAccountInput;
import com.rayllanderson.raybank.refund.util.RefundDescriptionUtil;
import com.rayllanderson.raybank.utils.MoneyUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.rayllanderson.raybank.shared.constants.DescriptionConstant.PAYMENT_DESCRIPTION;

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

    @NotNull
    protected String description;

    protected String message;

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

    @JsonIgnore
    public boolean isAccountDeposit() {
        return TransactionType.DEPOSIT.equals(this.getType()) && Objects.isNull(this.getDebit());
    }

    public static Transaction creditAccount(final CreditAccountInput input, final String referenceId, String message) {
        final var credit = new Credit(input.getAccountId(), Credit.Destination.ACCOUNT);
        final var debit = new Debit(input.getOrigin().getIdentifier(), Debit.Origin.valueOf(input.getOrigin().getType().name()));

        return Transaction.builder()
                .amount(MoneyUtils.from(input.getAmount()))
                .method(input.getTransactionMethod())
                .financialMovement(FinancialMovement.CREDIT)
                .moment(LocalDateTime.now())
                .description(input.getDescription())
                .debit(debit)
                .message(message)
                .credit(credit)
                .type(input.getTransactionType())
                .referenceId(referenceId)
                .accountId(input.getAccountId())
                .build();
    }

    public static Transaction debitAccount(final DebitAccountInput input, String referenceTransactionId) {
        final var credit = new Credit(input.getDestination().getIdentifier(), Credit.Destination.valueOf(input.getDestination().getType().name()));
        final var debit = new Debit(input.getAccountId(), Debit.Origin.ACCOUNT);

        return Transaction.builder()
                .type(input.getTransaction().getTransactionType())
                .method(input.getTransaction().getTransactionMethod())
                .referenceId(referenceTransactionId)
                .financialMovement(FinancialMovement.DEBIT)
                .amount(MoneyUtils.from(input.getAmount()))
                .moment(LocalDateTime.now())
                .message(input.getMessage())
                .description(input.getDescription())
                .accountId(input.getAccountId())
                .credit(credit)
                .debit(debit)
                .build();
    }

    public static Transaction depositAccount(final DepositAccountInput input) {
        final var credit = new Credit(input.accountId(), Credit.Destination.ACCOUNT);

        return Transaction.builder()
                .type(TransactionType.DEPOSIT)
                .method(TransactionMethod.ACCOUNT)
                .description("Depósito Recebido")
                .financialMovement(FinancialMovement.CREDIT)
                .amount(MoneyUtils.from(input.amount()))
                .moment(LocalDateTime.now())
                .accountId(input.accountId())
                .credit(credit)
                .build();
    }

    public static Transaction creditInvoice(BigDecimal amount, String accountId, Transaction debitTransaction) {
        final var credit = debitTransaction.getCredit();
        final var debit = debitTransaction.getDebit();
        return Transaction.builder()
                .amount(MoneyUtils.from(amount))
                .moment(LocalDateTime.now())
                .description(PAYMENT_DESCRIPTION)
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
                .description(RefundDescriptionUtil.fromOriginalTransaction(debitTransaction.getDescription()))
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
