package com.rayllanderson.raybank.models.statements;

import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.BankStatementType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BankStatement {

    @Id
    protected String id;
    @Column(columnDefinition = "TIMESTAMP")
    protected Instant moment;
    @Enumerated(EnumType.STRING)
    protected BankStatementType type;
    protected BigDecimal amount;
    protected String message;
    @ManyToOne
    private BankAccount accountOwner;

    public static TransferStatement createTransferBankStatement(BigDecimal amount, BankAccount accountSender, BankAccount accountOwner,
                                                          String message){
        return TransferStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.TRANSFER)
                .amount(amount)
                .message(message)
                .sender(accountSender)
                .owner(accountOwner)
                .build();
    }

    public static BankStatement createDepositBankStatement(BigDecimal amount, BankAccount accountOwner){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.DEPOSIT)
                .amount(amount)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement receivingCardPayment(BankAccount accountOwner, BankStatement originalBankStatement){
        final var bankStatement = BankStatement.builder()
                .moment(Instant.now())
                .type(BankStatementType.CARD_RECEIVE_PAYMENT)
                .amount(originalBankStatement.getAmount().abs())
                .message(originalBankStatement.message)
                .accountOwner(accountOwner)
                .build();
        return CardStatement.fromBankStatement(bankStatement, originalBankStatement);
    }

    public static BankStatement createBoletoPaymentBankStatement(BigDecimal amount, BankAccount accountOwner){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.BRAZILIAN_BOLETO)
                .amount(amount.negate())
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement createDebitCardBankStatement(BigDecimal amount, BankAccount accountOwner, String message){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.DEBIT_CARD_PAYMENT)
                .amount(amount.negate())
                .message(message)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement createCreditBankStatement(BigDecimal amount, BankAccount accountOwner, String message){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.CREDIT_CARD_PAYMENT)
                .amount(amount.negate())
                .message(message)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement createInvoicePaymentBankStatement(BigDecimal amount, BankAccount accountOwner){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.INVOICE_PAYMENT)
                .amount(amount)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BankStatement bankStatement = (BankStatement) o;
        return Objects.equals(id, bankStatement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PrePersist
    public void createId() {
        if (Objects.isNull(this.id))
            this.id = UUID.randomUUID().toString();
    }
}
