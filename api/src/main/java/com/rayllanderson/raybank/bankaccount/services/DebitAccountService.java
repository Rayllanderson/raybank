package com.rayllanderson.raybank.bankaccount.services;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.bankaccount.transactions.AccountPaymentTransaction;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DebitAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    //todo:: temporario
    @Transactional
    public Transaction debit(final DebitAccountInput debitInput){
        final BankAccount bankAccount = bankAccountRepository.findById(debitInput.getAccountId())
                .orElseThrow(() -> new NotFoundException("Conta bancária não existe"));

        var amountToBePaid = debitInput.getAmount();
        bankAccount.pay(amountToBePaid);

        this.bankAccountRepository.save(bankAccount);

        return transactionRepository.save(AccountPaymentTransaction.from(debitInput));
    }

}
