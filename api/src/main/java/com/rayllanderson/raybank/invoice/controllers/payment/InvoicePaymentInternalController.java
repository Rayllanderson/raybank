package com.rayllanderson.raybank.invoice.controllers.payment;

import com.rayllanderson.raybank.invoice.services.payment.InvoicePaymentInput;
import com.rayllanderson.raybank.invoice.services.payment.InvoicePaymentService;
import com.rayllanderson.raybank.core.security.method.RequiredAccountAndCardOwner;
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
@RequestMapping("api/v1/internal/accounts/{accountId}/cards/{cardId}")
@RequiredArgsConstructor
public class InvoicePaymentInternalController {

    private final InvoicePaymentService invoicePaymentService;

    @RequiredAccountAndCardOwner
    @PostMapping("/invoices/current/pay")
    public ResponseEntity<InvoicePaymentInternalResponse> payInvoice(@RequestBody @Valid InvoicePaymentInternalRequest request,
                                                                     @PathVariable String accountId,
                                                                     @PathVariable String cardId,
                                                                     @AuthenticationPrincipal Jwt jwt) {

        final var transaction = invoicePaymentService.payCurrent(new InvoicePaymentInput(request.getAmount(), accountId, cardId, null));

        return ResponseEntity.ok().body(InvoicePaymentInternalResponse.fromTransaction(transaction));
    }

    @RequiredAccountAndCardOwner
    @PostMapping("/invoices/{invoiceId}/pay")
    public ResponseEntity<InvoicePaymentInternalResponse> payInvoiceById(@RequestBody @Valid InvoicePaymentInternalRequest request,
                                                                         @PathVariable String accountId,
                                                                         @PathVariable String cardId,
                                                                         @PathVariable final String invoiceId,
                                                                         @AuthenticationPrincipal Jwt jwt) {

        final var transaction = invoicePaymentService.payById(new InvoicePaymentInput(request.getAmount(), accountId, cardId, invoiceId));

        return ResponseEntity.ok().body(InvoicePaymentInternalResponse.fromTransaction(transaction));
    }
}
