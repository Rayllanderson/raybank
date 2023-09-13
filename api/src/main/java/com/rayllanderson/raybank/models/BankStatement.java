package com.rayllanderson.raybank.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BankStatement {

    @Id
    private String id;
    @Column(columnDefinition = "TIMESTAMP")
    private Instant moment;
    @Enumerated(EnumType.STRING)
    private BankStatementType type;
    private BigDecimal amount;
    private String message;
    @ManyToOne
    private BankAccount accountSender;
    @ManyToOne
    private BankAccount accountOwner;

    public BankStatement(String id, Instant moment, BankStatementType type, BigDecimal amount, String message, BankAccount accountSender, BankAccount accountOwner) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
        this.moment = moment;
        this.type = type;
        this.amount = amount;
        this.message = message;
        this.accountSender = accountSender;
        this.accountOwner = accountOwner;
    }

    public static BankStatement createTransferBankStatement(BigDecimal amount, BankAccount accountSender, BankAccount accountOwner,
                                                          String message){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.TRANSFER)
                .amount(amount)
                .message(message)
                .accountSender(accountSender)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement createDepositBankStatement(BigDecimal amount, BankAccount accountOwner){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.DEPOSIT)
                .amount(amount)
                .accountSender(null)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement receivingCardPayment(BankAccount accountOwner, BankStatement originalBankStatement){
        final var bankStatement = BankStatement.builder()
                .moment(Instant.now())
                .type(BankStatementType.CARD_RECEIVE_PAYMENT)
                .amount(originalBankStatement.getAmount().abs())
                .accountSender(null)
                .message(originalBankStatement.message)
                .accountOwner(accountOwner)
                .build();
        return CardReceivementBankStatement.fromBankStatement(bankStatement, originalBankStatement);
    }

    public static BankStatement createBoletoPaymentBankStatement(BigDecimal amount, BankAccount accountOwner){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.BRAZILIAN_BOLETO)
                .amount(amount.negate())
                .accountSender(null)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement createDebitCardBankStatement(BigDecimal amount, BankAccount accountOwner, String message){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.DEBIT_CARD_PAYMENT)
                .amount(amount.negate())
                .accountSender(null)
                .message(message)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement createCreditBankStatement(BigDecimal amount, BankAccount accountOwner, String message){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.CREDIT_CARD_PAYMENT)
                .amount(amount.negate())
                .accountSender(null)
                .message(message)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement createInvoicePaymentBankStatement(BigDecimal amount, BankAccount accountOwner){
        return BankStatement.builder().
                moment(Instant.now())
                .type(BankStatementType.INVOICE_PAYMENT)
                .amount(amount)
                .accountSender(null)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    /**
     * @return nova instância de BankStatement sem ID e com valor de transação negativo.
     */
    public BankStatement toNegative(){
        BankStatement source = instantiateBankStatementFrom(this);
        source.setAccountSender(this.getAccountOwner());
        source.setAccountOwner(this.getAccountSender());
        source.id = UUID.randomUUID().toString();
        return source;
    }

    private static BankStatement instantiateBankStatementFrom(BankStatement source) {
        BankStatement bankStatement = new BankStatement();
        bankStatement.setMoment(source.getMoment());
        bankStatement.setAmount(source.getAmount());
        bankStatement.setMessage(source.getMessage());
        bankStatement.setType(source.getType());
        bankStatement.setAccountOwner(source.getAccountOwner());
        bankStatement.setAccountSender(source.getAccountSender());
        bankStatement.convertAmountToNegative();
        return bankStatement;
    }

    private void convertAmountToNegative(){
        this.amount = this.amount.negate();
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
}
