package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.bank.BankDepositDto;
import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;
import com.rayllanderson.raybank.dtos.responses.bank.BankAccountDto;
import com.rayllanderson.raybank.dtos.responses.bank.ContactResponseDto;
import com.rayllanderson.raybank.dtos.responses.bank.StatementDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.services.BankAccountService;
import com.rayllanderson.raybank.services.StatementFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/authenticated/bank-account")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final StatementFinderService statementFinderService;

    @GetMapping
    public ResponseEntity<BankAccountDto> findUserBankAccount(@AuthenticationPrincipal User authenticatedUser) {
        return ResponseEntity.ok(bankAccountService.findByUser(authenticatedUser));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody BankTransferDto transaction, @AuthenticationPrincipal User authenticatedUser) {
        transaction.setSender(authenticatedUser);
        bankAccountService.transfer(transaction);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody BankDepositDto transaction, @AuthenticationPrincipal User authenticatedUser) {
        transaction.setOwner(authenticatedUser);
        bankAccountService.deposit(transaction);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/statements")
    public ResponseEntity<List<StatementDto>> findAllStatements(@AuthenticationPrincipal User authenticatedUser) {
        return ResponseEntity.ok(statementFinderService.findAllAccountStatements(authenticatedUser.getBankAccount()));
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<ContactResponseDto>> findAllContacts(@AuthenticationPrincipal User authenticatedUser) {
        return ResponseEntity.ok(bankAccountService.findAllContactsByAccount(authenticatedUser.getBankAccount()));
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ContactResponseDto> findContactById(@PathVariable Long id, @AuthenticationPrincipal User authenticatedUser) {
        return ResponseEntity.ok(bankAccountService.findContactById(id, authenticatedUser.getBankAccount()));
    }
}
