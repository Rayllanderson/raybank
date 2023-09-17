package com.rayllanderson.raybank.statement.models;

import com.rayllanderson.raybank.users.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Setter
@Entity
public class CardStatement extends BankStatement {

    @ManyToOne
    private User establishment;
    private Integer installments;
}
