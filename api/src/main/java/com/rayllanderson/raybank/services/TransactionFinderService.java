package com.rayllanderson.raybank.services;


import com.rayllanderson.raybank.dtos.responses.bank.TransactionDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.Transaction;
import com.rayllanderson.raybank.models.TransactionType;
import com.rayllanderson.raybank.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransactionFinderService {

    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    public List<TransactionDto> findAllByAccountId(Long accountId) {
        return transactionRepository.findAllByAccountOwnerId(accountId).stream().map(TransactionDto::fromTransaction).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TransactionDto findByIdAndAccountId(String id, Long accountId) {
        var transaction = transactionRepository.findByIdAndAccountOwnerId(id, accountId).orElseThrow(() -> new BadRequestException(
                "Extrato não encontrado"));
        return TransactionDto.fromTransaction(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionDto> findAllAccountStatements(BankAccount account){
        List<Transaction> accountStatements = transactionRepository.findAllByAccountOwnerIdAndTypeNot(
                account.getId(), TransactionType.CREDIT_CARD_PAYMENT);
        return accountStatements.stream().map(TransactionDto::fromTransaction).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionDto> findAllCreditCardStatements(Long accountOwnerId){
        var creditCardStatements = transactionRepository.findAllByAccountOwnerIdAndType(
                accountOwnerId, TransactionType.CREDIT_CARD_PAYMENT
        );
        creditCardStatements.addAll(transactionRepository.findAllByAccountOwnerIdAndType(
                accountOwnerId, TransactionType.INVOICE_PAYMENT));
        return creditCardStatements.stream().map(TransactionDto::fromTransaction).collect(Collectors.toList());
    }
}
