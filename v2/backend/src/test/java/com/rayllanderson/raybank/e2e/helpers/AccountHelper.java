package com.rayllanderson.raybank.e2e.helpers;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountHelper {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void deposit(BigDecimal v, String accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).get();
        bankAccount.setBalance(v);
        bankAccountRepository.save(bankAccount);
    }

    public void deposit(double v, String accountId) {
        deposit(BigDecimal.valueOf(v), accountId);
    }

}
