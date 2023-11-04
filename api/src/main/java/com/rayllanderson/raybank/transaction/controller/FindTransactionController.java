package com.rayllanderson.raybank.transaction.controller;

import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Transaction;
import io.github.rayexpresslibraries.ddd.domain.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/transactions")
@RequiredArgsConstructor
public class FindTransactionController {
    private final TransactionGateway transactionGateway;

    @GetMapping
    public ResponseEntity<Pagination<Transaction>> findAll(@RequestParam String accountId, Pageable pageable) {
        return ResponseEntity.ok(transactionGateway.findAllByAccountId(accountId, pageable));
    }

    @GetMapping("{tId}")
    public ResponseEntity<Transaction> findById(@PathVariable String tId) {
        return ResponseEntity.ok().body(transactionGateway.findById(tId));
    }
}
