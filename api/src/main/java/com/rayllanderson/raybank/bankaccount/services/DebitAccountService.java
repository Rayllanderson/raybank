package com.rayllanderson.raybank.bankaccount.services;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.statement.models.BankStatementType;
import com.rayllanderson.raybank.statement.repository.BankStatementRepository;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DebitAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankStatementRepository bankStatementRepository;

    //todo:: temporario

    @Transactional
    public Transaction debit(final DebitAccountInput debitInput){
        final BankAccount bankAccount = bankAccountRepository.findById(debitInput.getAccountId())
                .orElseThrow(() -> new NotFoundException("Conta bancária não existe"));

        var amountToBePaid = debitInput.getAmount();
        bankAccount.pay(amountToBePaid);

        bankStatementRepository.save(BankStatement.createDebitStatement(amountToBePaid, bankAccount, BankStatementType.valueOf(debitInput.getOrigin().getType().name())));

        this.bankAccountRepository.save(bankAccount);

        return Transaction.builder().id(UUID.randomUUID().toString()).build();
    }

}
