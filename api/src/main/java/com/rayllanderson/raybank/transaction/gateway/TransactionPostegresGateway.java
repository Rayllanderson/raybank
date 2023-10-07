package com.rayllanderson.raybank.transaction.gateway;

import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionPostegresGateway implements TransactionGateway {
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction findById(String id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Transaction with id %s were found", id)));
    }
}
