package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.bank.BankPaymentDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.services.BankAccountService;
import com.rayllanderson.raybank.services.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users/authenticated/payment")
@RestController
public class PaymentController {

    private final BankAccountService bankAccountService;
    private final UserRepository userRepository;
    private final CreditCardService creditCardService;

    @PostMapping("/boleto")
    public ResponseEntity<Void> pay(@RequestBody BankPaymentDto transaction) {
        User sender = userRepository.findById(1L).get();
        transaction.setOwner(sender);
        bankAccountService.pay(transaction);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/credit-card")
    public ResponseEntity<Void> purchase(@RequestBody com.rayllanderson.raybank.dtos.requests.bank.CreditCardDto dto){
        User user = userRepository.findById(1L).get();
        dto.setAccount(user.getBankAccount());
        creditCardService.makePurchase(dto);
        return ResponseEntity.noContent().build();
    }

}
