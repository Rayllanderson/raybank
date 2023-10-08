package com.rayllanderson.raybank.boleto.controllers.payment;

import com.rayllanderson.raybank.boleto.services.payment.BoletoPaymentInput;
import com.rayllanderson.raybank.boleto.services.payment.BoletoPaymentService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/payments/boleto")
public class BoletoPaymentController {

    private final BoletoPaymentService boletoPaymentService;

    @PostMapping
    public ResponseEntity<BoletoPaymentResponse> pay(@Valid @RequestBody BoletoPaymentRequest request, @AuthenticationPrincipal Jwt jwt) {
        final var input = new BoletoPaymentInput(request.getBarCode(), JwtUtils.getAccountIdFrom(jwt));

        final var transaction = boletoPaymentService.pay(input);

        return ResponseEntity.ok(BoletoPaymentResponse.fromTransaction(transaction));
    }

}
