package com.rayllanderson.raybank.models.statements;

import com.rayllanderson.raybank.models.User;
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
