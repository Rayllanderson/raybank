package com.rayllanderson.raybank.controllers;


import com.rayllanderson.raybank.dtos.responses.bank.TransactionDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.services.TransactionFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users/authenticated/statements")
@RestController
public class TransactionController {

    private final TransactionFinderService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> findAllTransactions(@AuthenticationPrincipal User authenticatedUser) {
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(transactionService.findAllByAccountId(accountId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> findTransactionByIdAndUserId(@PathVariable String id, @AuthenticationPrincipal User authenticatedUser) {
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(transactionService.findByIdAndAccountId(id, accountId));
    }
}
