package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.responses.bank.CreditCardDto;
import com.rayllanderson.raybank.dtos.responses.bank.StatementDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.services.CreditCardService;
import com.rayllanderson.raybank.services.StatementFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/authenticated/bank-account/credit-card")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final UserRepository userRepository;
    private final StatementFinderService statementFinderService;

    @GetMapping
    public ResponseEntity<CreditCardDto> find(){
        User user = userRepository.findById(1L).get();
        Long accountId = user.getBankAccount().getId();
        return ResponseEntity.ok(CreditCardDto.fromCreditCard(creditCardService.findByAccountId(accountId)));
    }

    @GetMapping("/statements")
    public ResponseEntity<List<StatementDto>> findStatements(){
        User user = userRepository.findById(1L).get();
        Long accountId = user.getBankAccount().getId();
        return ResponseEntity.ok(statementFinderService.findAllCreditCardStatements(accountId));
    }

    @PostMapping("/pay/invoice")
    public ResponseEntity<Void> payInvoice(@RequestBody com.rayllanderson.raybank.dtos.requests.bank.CreditCardDto dto){
        User user = userRepository.findById(1L).get();
        dto.setAccount(user.getBankAccount());
        creditCardService.payInvoice(dto);
        return ResponseEntity.noContent().build();
    }
}
