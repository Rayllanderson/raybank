package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.bank.BankDepositDto;
import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;
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
@RequestMapping("api/v1/bank-account")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final BankAccountRepository repository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<BankAccount>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody BankTransferDto transaction){
        User sender = userRepository.findById(1L).get();
        transaction.setSender(sender);
        bankAccountService.transfer(transaction);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> transfer(@RequestBody BankDepositDto transaction){
        User sender = userRepository.findById(1L).get();
        transaction.setOwner(sender);
        bankAccountService.deposit(transaction);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/statement")
    public ResponseEntity<Void> getStatements(){
//        User sender = userRepository.findById(1L).get();
//        transaction.setOwner(sender);
//        bankAccountService.deposit(transaction);
        return ResponseEntity.ok().build();
    }


}
