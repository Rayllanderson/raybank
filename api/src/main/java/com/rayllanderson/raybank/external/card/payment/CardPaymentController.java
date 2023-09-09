package com.rayllanderson.raybank.external.card.payment;

import com.rayllanderson.raybank.models.CreditCard;
import com.rayllanderson.raybank.repositories.CreditCardRepository;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.services.creditcard.CreditCardService;
import com.rayllanderson.raybank.services.creditcard.inputs.PaymentCardInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/external/payments")
@RequiredArgsConstructor
public class CardPaymentController {

    private final CreditCardService creditCardService;
    private final UserRepository userRepository;
    private final CreditCardRepository creditCardRepository;

    @PostMapping("/card")
    public ResponseEntity<CardPaymentResponse> pay(@Valid @RequestBody PaymentCardRequest request) {
        final var input = PaymentCardInput.fromRequest(request);

        final var transaction = creditCardService.pay(input);

        return ResponseEntity.ok().body(CardPaymentResponse.fromTransaction(transaction));
    }

    @GetMapping("/teste")
    public ResponseEntity<?> find(@AuthenticationPrincipal Jwt jwt){
        var authenticatedUser = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(this.creditCardRepository.findByBankAccountId(accountId));
    }
}
