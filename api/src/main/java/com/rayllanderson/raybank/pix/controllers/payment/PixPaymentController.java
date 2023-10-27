package com.rayllanderson.raybank.pix.controllers.payment;

import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.pix.service.payment.PixPaymentInput;
import com.rayllanderson.raybank.pix.service.payment.PixPaymentMapper;
import com.rayllanderson.raybank.pix.service.payment.PixPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/internal/pix/payment")
@RequiredArgsConstructor
public class PixPaymentController {

    private final PixPaymentMapper mapper;
    private final PixPaymentService service;

    @PostMapping
    public ResponseEntity<PixPaymentResponse> transfer(@RequestBody @Valid PixPaymentRequest request,
                                                       @AuthenticationPrincipal Jwt jwt) {
        final var accountId = JwtUtils.getAccountIdFrom(jwt);

        final var payment = new PixPaymentInput(request.getQrCode(), accountId);
        final var response = service.pay(payment);

        return ResponseEntity.status(HttpStatus.OK).body(mapper.from(response));
    }
}
