package com.rayllanderson.raybank.card.controllers.payment;

import com.rayllanderson.raybank.card.services.payment.CardPaymentInput;
import com.rayllanderson.raybank.card.services.payment.CardPaymentService;
import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "cards")
@RestController
@RequestMapping("/api/v1/external/cards/payment")
@RequiredArgsConstructor
public class CardPaymentController {

    private final CardPaymentService cardPaymentService;
    private final PaymentCardMapper mapper;

    @PostMapping
    public ResponseEntity<CardPaymentResponse> pay(@Valid @RequestBody CardPaymentRequest request,
                                                   @AuthenticationPrincipal Jwt jwt) {
        final CardPaymentInput input = mapper.from(request, JwtUtils.getUserIdFrom(jwt));

        final var transaction = cardPaymentService.pay(input);

        return ResponseEntity.ok().body(CardPaymentResponse.fromTransaction(transaction));
    }
}
