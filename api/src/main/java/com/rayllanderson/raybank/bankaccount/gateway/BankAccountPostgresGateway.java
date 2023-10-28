package com.rayllanderson.raybank.bankaccount.gateway;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.ACCOUNT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class BankAccountPostgresGateway implements BankAccountGateway {

    private final BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount save(BankAccount bankAccount) {
        return this.bankAccountRepository.save(bankAccount);
    }

    @Override
    public void flush() {
        this.bankAccountRepository.flush();
    }

    @Override
    public BankAccount findById(final String id) {
        return bankAccountRepository.findById(id).orElseThrow(() -> NotFoundException.with(ACCOUNT_NOT_FOUND, "Conta bancária não existe"));
    }

    @Override
    public BankAccount findByIdOrNumber(String idOrNumber) {
        try {
            return findByNumber(Integer.parseInt(idOrNumber));
        } catch (NumberFormatException e) {
            return findById(idOrNumber);
        }
    }

    @Override
    public boolean existsByNumber(int number) {
        return bankAccountRepository.existsByNumber(number);
    }

    @Override
    public BankAccount findByNumber(int accountNumber) {
        return bankAccountRepository.findByNumber(accountNumber).orElseThrow(() -> NotFoundException.formatted(ACCOUNT_NOT_FOUND, "Conta bancária '%s' não existe", accountNumber));
    }
}
