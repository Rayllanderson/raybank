package com.rayllanderson.raybank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.external.boleto.model.Boleto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer accountNumber;
    private BigDecimal balance;
    @OneToOne(orphanRemoval = true)
    private CreditCard creditCard;
    @JsonIgnore
    @OneToOne
    private User user;
    @JsonIgnore
    @ManyToMany
    private Set<BankAccount> contacts = new HashSet<>();

    public BankAccount(Long id, Integer accountNumber, BigDecimal balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void receiveCardPayment(BigDecimal amount) {
        this.deposit(amount);
    }

    /**
     * Realiza a ação de transferir. Será reduzido o valor de transferência dessa conta e será adicionado
     * na conta de destino.
     *
     * @param recipient Conta a receber a transferência.
     * @param amountToBeTransferred Quantidade a ser transferida.
     */
    public void transferTo(BankAccount recipient, BigDecimal amountToBeTransferred){
        recipient.setBalance(recipient.getBalance().add(amountToBeTransferred));
        this.setBalance(this.getBalance().subtract(amountToBeTransferred));
    }

    public void pay (BigDecimal amount) throws UnprocessableEntityException{
        if (this.hasAvailableBalance(amount)){
            this.balance = this.balance.subtract(amount);
        } else throw new UnprocessableEntityException("Sua conta não tem saldo disponível");
    }

    public void pay (Boleto boleto) throws UnprocessableEntityException {
        if (boleto.isExpired()) {
            throw new UnprocessableEntityException("Boleto "+ boleto.getCode() + " vencido");
        }
        if (boleto.isPaid()){
            throw new UnprocessableEntityException("Boleto "+ boleto.getCode() + " já foi pago");
        }
        this.pay(boleto.getValue());
        boleto.liquidate();
    }

    /**
     * Realiza a ação de depositar. Soma e adiciona o valor a conta
     * @param amount Será adicionada essa quantia no saldo da conta.
     */
    public void deposit(BigDecimal amount){
        this.setBalance(this.getBalance().add(amount));
    }

    /**
     * Compara e verifica se a conta atual tem saldo disponível
     *
     * @param amount quantia a ser comparada com o saldo da conta
     *
     * @return true se a conta atual tiver saldo disponível. False se não.
     */
    public boolean hasAvailableBalance(BigDecimal amount){
        return this.getBalance().compareTo(amount) >= 0;
    }

    public void addContact(BankAccount contact) {
        this.contacts.add(contact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BankAccount account = (BankAccount) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void attacthUser(User user) {
        this.user = user;
    }

    public boolean sameCard(final CreditCard creditCard) {
        return this.getCreditCard().getId().equals(creditCard.getId());
    }
}
