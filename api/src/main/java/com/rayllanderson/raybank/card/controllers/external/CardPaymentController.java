package com.rayllanderson.raybank.card.controllers.external;

import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.users.repository.UserRepository;
import com.rayllanderson.raybank.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.card.services.payment.CardPaymentService;
import com.rayllanderson.raybank.card.services.create.CreateCardService;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/external/payments")
@RequiredArgsConstructor
public class CardPaymentController {

    private final CreateCardService createCardService;
    private final CardPaymentService cardPaymentService;
    private final UserRepository userRepository;
    private final CreditCardRepository creditCardRepository;

    @PostMapping("/card")
    public ResponseEntity<CardPaymentResponse> pay(@Valid @RequestBody PaymentCardRequest request,
                                                   @AuthenticationPrincipal Jwt jwt) {
        final var input = PaymentCardInput.fromRequest(request);
        input.setEstablishmentId(JwtUtils.getUserIdFrom(jwt));

        final var transaction = cardPaymentService.pay(input);

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
