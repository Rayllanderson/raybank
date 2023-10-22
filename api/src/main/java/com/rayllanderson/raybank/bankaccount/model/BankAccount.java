package com.rayllanderson.raybank.bankaccount.model;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.users.model.User;
import com.rayllanderson.raybank.users.model.UserType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccount {

    @Id
    private String id;

    private Integer number;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private BankAccountType type;

    @Enumerated(EnumType.STRING)
    private BankAccountStatus status;

    private LocalDateTime createAt;

    @OneToOne(orphanRemoval = true)
    private Card card;

    @OneToOne
    private User user;

    @ManyToMany
    private Set<BankAccount> contacts;

    public static BankAccount create(int number, BankAccountType type, String userId) {
        return BankAccount.builder()
                .id(userId)
                .number(number)
                .balance(BigDecimal.ZERO)
                .createAt(LocalDateTime.now())
                .contacts(new HashSet<>())
                .type(type)
                .status(BankAccountStatus.ACTIVE)
                .user(User.fromId(userId)).build();
    }

    public static BankAccount createFromUserType(int number, UserType userType, String userId) {
        return UserType.USER.equals(userType) ?
                createNormal(number, userId) :
                createEstablishment(number, userId);
    }

    public static BankAccount createEstablishment(int number, String userId) {
        return create(number, BankAccountType.ESTABLISHMENT, userId);
    }

    public static BankAccount createNormal(int number, String userId) {
        return create(number, BankAccountType.NORMAL, userId);
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

    /**
     * Realiza a ação de depositar. Soma e adiciona o valor a conta
     * @param amount Será adicionada essa quantia no saldo da conta.
     */
    public void deposit(BigDecimal amount){
        this.setBalance(this.getBalance().add(amount));
    }

    public void credit(final BigDecimal amount) {
        if (amount == null) return;
        this.balance = balance.add(amount);
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

    public static BankAccount withId(String id) {
        return BankAccount.builder().id(id).build();
    }

    public boolean sameCard(final Card card) {
        return Optional.ofNullable(this.getCard())
                .stream()
                .anyMatch(c -> c.getId().equals(card.getId()));
    }

    public boolean isEstablishment() {
        return BankAccountType.ESTABLISHMENT.equals(this.type);
    }

    public boolean isActive() {
        return BankAccountStatus.ACTIVE.equals(this.status);
    }

    public boolean isInactive() {
        return BankAccountStatus.INACTIVE.equals(this.status);
    }
}
