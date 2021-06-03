package com.rayllanderson.raybank.models;

import com.rayllanderson.raybank.models.enums.StatementType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankStatement {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime moment;
    @Enumerated(EnumType.STRING)
    private StatementType statementType;
    private BigDecimal amount;
    private String message;
    @ManyToOne
    private BankAccount accountSender;
    @ManyToOne
    private BankAccount accountOwner;

    public static BankStatement createTransferStatement(BigDecimal amount, BankAccount accountSender, BankAccount accountOwner,
                                                        String message){
        return BankStatement.builder().
                moment(LocalDateTime.now())
                .statementType(StatementType.TRANSFER)
                .amount(amount)
                .message(message)
                .accountSender(accountSender)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement createDepositStatement (BigDecimal amount, BankAccount accountOwner){
        return BankStatement.builder().
                moment(LocalDateTime.now())
                .statementType(StatementType.DEPOSIT)
                .amount(amount)
                .accountSender(null)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    /**
     * Usando quando faz uma compra via boleto
     * @return BankStatement setado com [StatementType.BRAZILIAN_BOLETO] e já nega q quantia [amount.negate()]
     */
    public static BankStatement createBoletoPaymentStatement (BigDecimal amount, BankAccount accountOwner){
        return BankStatement.builder().
                moment(LocalDateTime.now())
                .statementType(StatementType.BRAZILIAN_BOLETO)
                .amount(amount.negate())
                .accountSender(null)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    /**
     * Usando quando realiza uma compra no cartão de crédito
     * @return BankStatement setado com [StatementType.CREDIT_CARD_PURCHASE] e já nega q quantia [amount.negate()]
     */
    public static BankStatement createCreditPurchaseStatement (BigDecimal amount, BankAccount accountOwner){
        return BankStatement.builder().
                moment(LocalDateTime.now())
                .statementType(StatementType.CREDIT_CARD_PAYMENT)
                .amount(amount.negate())
                .accountSender(null)
                .message(null)
                .accountOwner(accountOwner)
                .build();
    }

    /**
     * Usado quando se paga faturas de cartão de crédito;
     * Não é setado pro negativo.
     * Exemplo: Pagou a fatura no valor de xxx necessita ser positivo
     * @return BankStatement setado com [StatementType.INVOICE_PAYMENT];
     */
    public static BankStatement createInvoicePaymentStatement (BigDecimal amount, BankAccount accountOwner){
        return BankStatement.builder().
                moment(LocalDateTime.now())
                .statementType(StatementType.INVOICE_PAYMENT)
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
        BankStatement source = instantiateStatementFrom(this);
        source.setAccountSender(this.getAccountOwner());
        source.setAccountOwner(this.getAccountSender());
        return source;
    }

    private static BankStatement instantiateStatementFrom(BankStatement source) {
        BankStatement statement = new BankStatement();
        statement.setMoment(source.getMoment());
        statement.setAmount(source.getAmount());
        statement.setMessage(source.getMessage());
        statement.setStatementType(source.getStatementType());
        statement.setAccountOwner(source.getAccountOwner());
        statement.setAccountSender(source.getAccountSender());
        statement.convertAmountToNegative();
        return statement;
    }


    private void convertAmountToNegative(){
        this.amount = this.amount.negate();
    }

    @Override
    public String toString() {
        return "BankStatement{" + "id=" + id + ", moment=" + moment + ", statementType=" + statementType + ", amount=" + amount + "," +
                " message='" + message + '\'' + '}';
    }
}
