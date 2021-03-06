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

import javax.validation.Valid;
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
    public ResponseEntity<Void> transfer(@RequestBody @Valid BankTransferDto transaction,
                                         @AuthenticationPrincipal User authenticatedUser) {
        transaction.setSenderId(authenticatedUser.getId());
        bankAccountService.transfer(transaction);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody @Valid BankDepositDto transaction,
                                        @AuthenticationPrincipal User authenticatedUser) {
        transaction.setOwnerId(authenticatedUser.getId());
        bankAccountService.deposit(transaction);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statements")
    public ResponseEntity<List<StatementDto>> findAllStatements(@AuthenticationPrincipal User authenticatedUser) {
        return ResponseEntity.ok(statementFinderService.findAllAccountStatements(authenticatedUser.getBankAccount()));
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<ContactResponseDto>> findAllContacts(@AuthenticationPrincipal User authenticatedUser) {
        return ResponseEntity.ok(bankAccountService.findAllContactsUserId(authenticatedUser.getId()));
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ContactResponseDto> findContactById(@PathVariable Long id, @AuthenticationPrincipal User authenticatedUser) {
        return ResponseEntity.ok(bankAccountService.findContactById(id, authenticatedUser.getId()));
    }
}
