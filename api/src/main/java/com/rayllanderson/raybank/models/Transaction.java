package com.rayllanderson.raybank.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    private String id;
    @Column(columnDefinition = "TIMESTAMP")
    private Instant moment;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private BigDecimal amount;
    private String message;
    @ManyToOne
    private BankAccount accountSender;
    @ManyToOne
    private BankAccount accountOwner;

    public Transaction(String id, Instant moment, TransactionType type, BigDecimal amount, String message, BankAccount accountSender, BankAccount accountOwner) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
        this.moment = moment;
        this.type = type;
        this.amount = amount;
        this.message = message;
        this.accountSender = accountSender;
        this.accountOwner = accountOwner;
    }

    public static Transaction createTransferTransaction(BigDecimal amount, BankAccount accountSender, BankAccount accountOwner,
                                                        String message){
        return Transaction.builder().
                moment(Instant.now())
                .type(TransactionType.TRANSFER)
                .amount(amount)
                .message(message)
                .accountSender(accountSender)
                .accountOwner(accountOwner)
                .build();
    }

    public static Transaction createDepositTransaction(BigDecimal amount, BankAccount accountOwner){
        return Transaction.builder().
                moment(Instant.now())
                .type(TransactionType.DEPOSIT)
                .amount(amount)
                .accountSender(null)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    public static Transaction createBoletoPaymentTransaction(BigDecimal amount, BankAccount accountOwner){
        return Transaction.builder().
                moment(Instant.now())
                .type(TransactionType.BRAZILIAN_BOLETO)
                .amount(amount.negate())
                .accountSender(null)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    public static Transaction createDebitCardTransaction(BigDecimal amount, BankAccount accountOwner){
        return Transaction.builder().
                moment(Instant.now())
                .type(TransactionType.DEBIT_CARD_PAYMENT)
                .amount(amount.negate())
                .accountSender(null)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    public static Transaction createCreditTransaction(BigDecimal amount, BankAccount accountOwner){
        return Transaction.builder().
                moment(Instant.now())
                .type(TransactionType.CREDIT_CARD_PAYMENT)
                .amount(amount.negate())
                .accountSender(null)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    public static Transaction createInvoicePaymentTransaction(BigDecimal amount, BankAccount accountOwner){
        return Transaction.builder().
                moment(Instant.now())
                .type(TransactionType.INVOICE_PAYMENT)
                .amount(amount)
                .accountSender(null)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    /**
     * @return nova instância de BankStatement sem ID e com valor de transação negativo.
     */
    public Transaction toNegative(){
        Transaction source = instantiateTransactionFrom(this);
        source.setAccountSender(this.getAccountOwner());
        source.setAccountOwner(this.getAccountSender());
        return source;
    }

    private static Transaction instantiateTransactionFrom(Transaction source) {
        Transaction transaction = new Transaction();
        transaction.setMoment(source.getMoment());
        transaction.setAmount(source.getAmount());
        transaction.setMessage(source.getMessage());
        transaction.setType(source.getType());
        transaction.setAccountOwner(source.getAccountOwner());
        transaction.setAccountSender(source.getAccountSender());
        transaction.convertAmountToNegative();
        return transaction;
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
        Transaction transaction = (Transaction) o;
        return Objects.equals(id, transaction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
