package com.rayllanderson.raybank.bankaccount.gateway;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankAccountPostgresGateway implements BankAccountGateway {

    private final BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount save(BankAccount bankAccount) {
        return this.bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount findById(final String id) {
        return bankAccountRepository.findById(id).orElseThrow(() -> new NotFoundException("Conta bancária não existe"));
    }

    @Override
    public boolean existsByNumber(int number) {
        return bankAccountRepository.existsByNumber(number);
    }
}
