package com.rayllanderson.raybank.statement.models;

import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.BankStatementType;
import com.rayllanderson.raybank.users.model.User;
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
    protected String transactionId;
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

    public static TransferStatement createTransferBankStatement(BigDecimal amount, BankAccount accountSender, BankAccount accountOwner,
                                                                String message, String transactionId) {
        return TransferStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.TRANSFER)
                .amount(amount)
                .message(message)
                .sender(accountSender)
                .owner(accountOwner)
                .transactionId(transactionId)
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

    public static BankStatement createDepositBankStatement(BigDecimal amount, BankAccount accountOwner, String transactionId){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.DEPOSIT)
                .amount(amount)
                .message(null)
                .accountOwner(accountOwner)
                .transactionId(transactionId)
                .build();
    }

    public static BankStatement receivingCardPayment(BankAccount accountOwner, BankStatement originalBankStatement){
        return BankStatement.builder()
                .moment(Instant.now())
                .type(BankStatementType.CARD_RECEIVE_PAYMENT)
                .amount(originalBankStatement.getAmount().abs())
                .message(originalBankStatement.message)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement receivingCardPayment(BankAccount accountOwner,
                                                     BigDecimal amount,
                                                     String message,
                                                     String transactionId,
                                                     int installments) {
        return CardStatement.builder()
                .moment(Instant.now())
                .type(BankStatementType.CARD_RECEIVE_PAYMENT)
                .amount(amount)
                .message(message)
                .transactionId(transactionId)
                .accountOwner(accountOwner)
                .installments(installments)
                .build();
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

    public static BankStatement createBoletoPaymentBankStatement(BigDecimal amount, BankAccount accountOwner, String transactionId){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.BRAZILIAN_BOLETO)
                .amount(amount.negate())
                .message(null)
                .accountOwner(accountOwner)
                .transactionId(transactionId)
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

    public static BankStatement createDebitCardBankStatement(BigDecimal amount, BankAccount accountOwner, String message, String transactionId, User establishment){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.DEBIT_CARD_PAYMENT)
                .amount(amount.negate())
                .message(message)
                .accountOwner(accountOwner)
                .transactionId(transactionId)
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

    public static BankStatement createCreditBankStatement(BigDecimal amount, BankAccount accountOwner, String message, String transactionId,
                                                          int installments, User establishment){
        return CardStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.CREDIT_CARD_PAYMENT)
                .amount(amount.negate())
                .message(message)
                .accountOwner(accountOwner)
                .transactionId(transactionId)
                .installments(installments)
                .establishment(establishment)
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

    public static BankStatement createInvoicePaymentBankStatement(BigDecimal amount, BankAccount accountOwner, String transactionId){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.INVOICE_PAYMENT)
                .amount(amount)
                .message(null)
                .accountOwner(accountOwner)
                .transactionId(transactionId)
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
