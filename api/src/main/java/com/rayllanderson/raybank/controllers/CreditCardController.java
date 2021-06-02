package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.responses.bank.CreditCardDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.services.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users/authenticated/bank-account/credit-card")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<CreditCardDto> find(){
        User user = userRepository.findById(1L).get();
        Long accountId = user.getBankAccount().getId();
        return ResponseEntity.ok(CreditCardDto.fromCreditCard(creditCardService.findByAccountId(accountId)));
    }

    @GetMapping("/statements")
    public ResponseEntity<CreditCardDto> findStatements(){
        return null;
    }

    //TESTE
    @PostMapping
    public ResponseEntity<Void> purchase(@RequestBody com.rayllanderson.raybank.dtos.requests.bank.CreditCardDto dto){
        User user = userRepository.findById(1L).get();
        dto.setAccount(user.getBankAccount());
        creditCardService.makePurchase(dto);
        return ResponseEntity.noContent().build();
    }

    //TESTE
    @PostMapping("/invoice")
    public ResponseEntity<Void> payInvoice(@RequestBody com.rayllanderson.raybank.dtos.requests.bank.CreditCardDto dto){
        User user = userRepository.findById(1L).get();
        dto.setAccount(user.getBankAccount());
        creditCardService.payInvoice(dto);
        return ResponseEntity.noContent().build();
    }
}
