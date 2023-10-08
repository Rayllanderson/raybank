package com.rayllanderson.raybank.card.controllers.external;

import com.rayllanderson.raybank.card.services.payment.CardPaymentService;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/external/payments")
@RequiredArgsConstructor
public class CardPaymentController {

    private final CardPaymentService cardPaymentService;

    @PostMapping("/card")
    public ResponseEntity<CardPaymentResponse> pay(@Valid @RequestBody PaymentCardRequest request,
                                                   @AuthenticationPrincipal Jwt jwt) {
        final var input = PaymentCardInput.fromRequest(request);
        input.setEstablishmentId(JwtUtils.getUserIdFrom(jwt));

        final var transaction = cardPaymentService.pay(input);

        return ResponseEntity.ok().body(CardPaymentResponse.fromTransaction(transaction));
    }
}
