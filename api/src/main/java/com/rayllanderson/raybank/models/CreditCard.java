package com.rayllanderson.raybank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
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
    private Integer securityCode;
    private YearMonth expiryDate;
    private BigDecimal balance;
    private BigDecimal invoice;
    @JsonIgnore
    @OneToOne
    private BankAccount bankAccount;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE)
    private List<BankStatement> statements = new ArrayList<>();

    public void payTheInvoice(BigDecimal amount) throws IllegalArgumentException, BadRequestException {
        if (this.hasInvoice()) {
            if (this.bankAccount.hasAvailableBalance(amount)) {
                if (isAmountGreaterThanInvoice(amount)) {
                    throw new IllegalArgumentException("O valor recebido é superior ao da fatura.");
                }
                invoice = invoice.subtract(amount);
                balance = balance.add(amount);
                bankAccount.pay(amount);
                createInvoiceStatement(amount);
            } else {
                throw new BadRequestException("Sua conta não possui saldo suficiente para pagar a fatura.");
            }
        } else {
            throw new BadRequestException("Ops, parece que seu cartão não possui nenhuma fatura.");
        }
    }

    public void makeCreditPurchase(BigDecimal amount) throws UnprocessableEntityException {
        if (this.hasLimit()) {
            if (isAmountGreaterThanBalance(amount)) {
                throw new UnprocessableEntityException("Falha na transação. O valor da compra é maior que seu saldo disponível no cartão.");
            }
            invoice = invoice.add(amount);
            balance = balance.subtract(amount);
            this.createPurchaseStatement(amount);
        } else
            throw new UnprocessableEntityException("Seu cartão não possui saldo suficiente para esta compra.");
    }

    public void makeDebitPurchase(BigDecimal amount) throws UnprocessableEntityException {
        try {
            this.bankAccount.pay(amount);
            this.createDebitStatement(amount);
        } catch (UnprocessableEntityException e) {
            throw new UnprocessableEntityException("Saldo em conta insuficiente para efetuar compra no débito");
        }
    }

    private void createInvoiceStatement(BigDecimal amount) {
        var statement = BankStatement.createInvoicePaymentStatement(amount, bankAccount);
        this.getStatements().add(statement);
    }

    private void createDebitStatement(BigDecimal amount) {
        var statement = BankStatement.createDebitCardPurchaseStatement(amount, bankAccount);
        this.getStatements().add(statement);
    }

    private void createPurchaseStatement(BigDecimal amount) {
        var statement = BankStatement.createCreditPurchaseStatement(amount, bankAccount);
        this.getStatements().add(statement);
    }

    public void payInvoiceAndRefundRemaining(BigDecimal amount) {
        BigDecimal refund = amount.subtract(invoice);
        this.payTheInvoice(amount.subtract(refund));
    }

    public boolean isValidSecurityCode(final Integer securityCode) {
        return Objects.equals(this.securityCode, securityCode);
    }

    public boolean isValidExpiryDate(final YearMonth expiryDate) {
        return Objects.equals(this.expiryDate, expiryDate);
    }

    public boolean isAmountGreaterThanBalance(BigDecimal amount) {
        return amount.compareTo(balance) > 0;
    }

    public boolean isAmountGreaterThanInvoice(BigDecimal amount) {
        return amount.compareTo(invoice) > 0;
    }

    public boolean hasLimit() {
        return !(balance.equals(BigDecimal.ZERO) || balance.equals(new BigDecimal("0.00")));
    }

    public boolean hasInvoice() {
        return !(invoice.equals(BigDecimal.ZERO) || invoice.equals(new BigDecimal("0.00")));
    }

    @Override
    public String toString() {
        return "CreditCard{" + "id=" + id + ", cardNumber=" + cardNumber + ", balance=" + balance + ", invoice=" + invoice + '}';
    }
}
