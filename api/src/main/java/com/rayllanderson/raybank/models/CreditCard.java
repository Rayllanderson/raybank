package com.rayllanderson.raybank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditCard {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long cardNumber;
    private BigDecimal balance;
    private BigDecimal invoice;
    @JsonIgnore
    @OneToOne
    private BankAccount bankAccount;

    /**
     * Realiza o pagamento da fatura.
     * Verifica se existe fatura;
     * Verifica se o valor da compra é maior que o valor da fatura.
     * @param amount valor da compra
     * @throws BadRequestException caso o cartão não tenha faturas
     * @throws IllegalArgumentException caso o valor da compra seja maior que o valor da fatura.
     */
    public void payTheInvoice(BigDecimal amount) throws IllegalArgumentException, BadRequestException {
        if (this.hasInvoice()) {
           if(this.bankAccount.hasAvailableBalance(amount)) {
               if (isAmountGreaterThanInvoice(amount)) {
                   throw new IllegalArgumentException("O valor recebido é superior ao da fatura.");
               }
               invoice = invoice.subtract(amount);
               balance = balance.add(amount);
               bankAccount.setBalance(bankAccount.getBalance().subtract(amount));
           }else {
               throw new BadRequestException("Sua conta não possui saldo suficiente para pagar a fatura.");
           }
        } else {
            throw new BadRequestException("Ops, parece que seu cartão não possui nenhuma fatura.");
        }
    }

    /**
     * Realiza um pagamento.
     * Verifica se tem limite;
     * Verifica se o valor da compra é maior que o saldo do cartão disponível
     * @param amount valor da compra
     * @throws BadRequestException caso falhe em uma das verificações
     */
    public void makePurchase(BigDecimal amount) throws BadRequestException {
        if (this.hasLimit()) {
            if (isAmountGreaterThanBalance(amount)) {
                throw new BadRequestException("Falha na transação. O valor da compra é maior que seu saldo disponível no cartão.");
            }
            invoice = invoice.add(amount);
            balance = balance.subtract(amount);
        } else throw new BadRequestException("Seu cartão não possui saldo suficiente para esta compra.");
    }

    /**
     * Calcula a diferença entre amount e a fatura, gerando o reembolso.
     * Adiciona o valor de reembolso na conta bancária e realiza o pagamento completo da fatura.
     */
    public void payInvoiceAndRefundRemaining(BigDecimal amount){
        BigDecimal refund = amount.subtract(invoice);
        this.payTheInvoice(amount.subtract(refund));
    }

    /**
     * @return true caso o valor do parameter seja maior que o saldo disponível
     */
    public boolean isAmountGreaterThanBalance(BigDecimal amount) {
        return amount.compareTo(balance) > 0;
    }

    /**
     * @return true caso o valor do parameter seja maior que a fatura atual
     */
    public boolean isAmountGreaterThanInvoice(BigDecimal amount) {
        return amount.compareTo(invoice) > 0;
    }

    /**
     * @return true caso o cartão tenha saldo disponível
     */
    public boolean hasLimit() {
        return !balance.equals(BigDecimal.ZERO);
    }

    /**
     * @return true caso o cartão tenha algum valor na fatura
     */
    public boolean hasInvoice() {
        return !invoice.equals(BigDecimal.ZERO);
    }

    @Override
    public String toString() {
        return "CreditCard{" + "id=" + id + ", cardNumber=" + cardNumber + ", balance=" + balance + ", invoice=" + invoice + '}';
    }
}
