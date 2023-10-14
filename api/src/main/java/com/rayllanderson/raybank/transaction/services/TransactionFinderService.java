package com.rayllanderson.raybank.transaction.services;


import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionFinderService {

    private static final List<TransactionType> CREDIT_CARD_TRANSACTIONS = List.of(TransactionType.CREDIT_CARD_PAYMENT, TransactionType.INVOICE_PAYMENT_RECEIPT);
    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    public List<Transaction> findAllByAccountId(final String accountId) {
        return transactionRepository.findAllByAccountId(accountId);
//                .stream()
//                .map(BankStatementDto::fromBankStatement)
//                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAllAccountStatementsByAccountId(final String accountId){
        return transactionRepository.findAllByAccountIdAndTypeNotIn(
                accountId, CREDIT_CARD_TRANSACTIONS);
//        return accountStatements.stream().map(BankStatementDto::fromBankStatement).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAllCardStatementsByUserId(String accountId){
        return transactionRepository.findAllByAccountIdAndTypeIn(
                accountId, CREDIT_CARD_TRANSACTIONS
        );
//        return creditCardStatements.stream().map(BankStatementDto::fromBankStatement).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Transaction findById(final String id) {
        return transactionRepository.findById(id).orElseThrow(() -> new BadRequestException(
                "Extrato não encontrado"));
//        return BankStatementDto.fromBankStatement(Transaction);
    }

    public Transaction findByIdAndAccountId(final String id, String accountId) {
        return transactionRepository.findByIdAndAccountId(id, accountId).orElseThrow(() -> new BadRequestException(
                "Extrato não encontrado"));
    }
}
