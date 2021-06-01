package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.bank.BankDepositDto;
import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;
import com.rayllanderson.raybank.dtos.responses.bank.BankAccountDto;
import com.rayllanderson.raybank.dtos.responses.bank.StatementDto;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.BankAccountRepository;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/authenticated/bank-account")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final BankAccountRepository repository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<BankAccountDto> findUserBankAccount() {
        User user = userRepository.findById(1L).get();
        return ResponseEntity.ok(bankAccountService.findByUser(user));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody BankTransferDto transaction) {
        User sender = userRepository.findById(1L).get();
        transaction.setSender(sender);
        bankAccountService.transfer(transaction);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> transfer(@RequestBody BankDepositDto transaction) {
        User sender = userRepository.findById(1L).get();
        transaction.setOwner(sender);
        bankAccountService.deposit(transaction);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/statements")
    public ResponseEntity<List<StatementDto>> findAllStatements() {
        User owner = userRepository.findById(1L).get();
        return ResponseEntity.ok(bankAccountService.findAllStatements(owner.getBankAccount()));
    }

    @GetMapping("/statements/{id}")
    public ResponseEntity<StatementDto> findStatementById(@PathVariable Long id) {
        User owner = userRepository.findById(1L).get();
        return ResponseEntity.ok(bankAccountService.findStatementsById(id, owner.getBankAccount()));
    }


}
