package com.rayllanderson.raybank.external.card.payment;

import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.CreditCardRepository;
import com.rayllanderson.raybank.services.CreditCardService;
import com.rayllanderson.raybank.services.inputs.PaymentCardInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/external/payments")
@RequiredArgsConstructor
public class CardPaymentController {

    private final CreditCardService creditCardService;
    private final CreditCardRepository creditCardRepository;

    @PostMapping("/card")
    public ResponseEntity<CardPaymentResponse> pay(@Valid @RequestBody PaymentCardRequest request) {
        final var input = PaymentCardInput.fromRequest(request);

        final var transaction = creditCardService.pay(input);

        return ResponseEntity.ok().body(CardPaymentResponse.fromTransaction(transaction));
    }

    @GetMapping("/teste")
    public ResponseEntity<?> find(@AuthenticationPrincipal User authenticatedUser){
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(this.creditCardRepository.findByBankAccountId(accountId));
    }
}
