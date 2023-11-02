package com.rayllanderson.raybank.refund.controller;

import com.rayllanderson.raybank.refund.service.ProcessRefundInput;
import com.rayllanderson.raybank.refund.service.ProceessRefundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/external/transactions")
@RequiredArgsConstructor
public class RefundController {

    private final ProceessRefundService refundPaymentService;

    @PostMapping("/{transactionId}/refund")
    public ResponseEntity<RefundResponse> find(@Valid @RequestBody RefundRequest request,
                                               @PathVariable String transactionId,
                                               @AuthenticationPrincipal Jwt jwt) {

        final var input = new ProcessRefundInput(transactionId, request.getAmount(), request.getReason());
        final var output = refundPaymentService.process(input);

        return ResponseEntity.ok().body(RefundResponse.fromOutput(output, request.getReason()));
    }
}
