package com.rayllanderson.raybank.bankaccount.services.create;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateBankAccountOutput {
    private String id;
    private int number;

    protected static CreateBankAccountOutput from(final BankAccount bankAccount) {
        return new CreateBankAccountOutput(bankAccount.getId(), bankAccount.getNumber());
    }
}
