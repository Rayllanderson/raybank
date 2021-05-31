package com.rayllanderson.raybank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long cardNumber;
    private BigDecimal balance;
    private BigDecimal invoice;
    @JsonIgnore
    @OneToOne
    private BankAccount bankAccount;

    public void payTheInvoice(BigDecimal amount){
        invoice = invoice.subtract(amount);
        balance = balance.add(amount);
    }

    public void makePurchase(BigDecimal amount){
        invoice = invoice.add(amount);
        balance = balance.subtract(amount);
    }

    public boolean hasLimit(){
        return balance.equals(BigDecimal.ZERO);
    }
}
