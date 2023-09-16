package com.rayllanderson.raybank.statement.models;

import com.rayllanderson.raybank.models.BankAccount;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Entity
public class TransferStatement extends BankStatement {

    @ManyToOne
    private BankAccount sender;
    @ManyToOne
    private BankAccount owner;

    /**
     * @return nova instância de BankStatement, com valor de transação negativo e alternando sender e owner.
     */
    public TransferStatement invert() {
        final TransferStatement source = instantiateBankStatementFrom(this);
        source.sender = this.owner;
        source.owner = this.sender;
        source.id = UUID.randomUUID().toString();
        return source;
    }

    private static TransferStatement instantiateBankStatementFrom(TransferStatement source) {
        TransferStatement bankStatement = new TransferStatement();
        bankStatement.setMoment(source.moment);
        bankStatement.setAmount(source.amount.negate());
        bankStatement.setMessage(source.message);
        bankStatement.setType(source.type);
        bankStatement.setOwner(source.owner);
        bankStatement.setSender(source.sender);
        return bankStatement;
    }
}
