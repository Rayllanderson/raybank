package com.rayllanderson.raybank.controllers;


import com.rayllanderson.raybank.dtos.responses.bank.StatementDto;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.services.StatementFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users/authenticated/statements")
@RestController
public class StatementController {

    private final StatementFinderService statementService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<StatementDto>> findAllStatements() {
        Long accountId = userRepository.findById(1L).get().getBankAccount().getId();
        return ResponseEntity.ok(statementService.findAllByAccountId(accountId));
    }

    @GetMapping("/statements/{id}")
    public ResponseEntity<StatementDto> findStatementById(@PathVariable Long id) {
        Long accountId = userRepository.findById(1L).get().getBankAccount().getId();
        return ResponseEntity.ok(statementService.findByIdAndAccountId(id, accountId));
    }
}
