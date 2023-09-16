package com.rayllanderson.raybank.invoice.controllers;

import com.rayllanderson.raybank.invoice.services.InvoicePaymentService;
import com.rayllanderson.raybank.invoice.services.PayInvoiceInput;
import com.rayllanderson.raybank.statement.models.BankStatement;
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

import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getUserIdFrom;

@RestController
@RequestMapping("api/v1/internal/credit-card")
@RequiredArgsConstructor
public class PayInvoiceInternalController {

    private final InvoicePaymentService invoicePaymentService;

    @PostMapping("/invoices/current")
    public ResponseEntity<PayInvoiceInternalResponse> payInvoice(@RequestBody @Valid PayInvoiceInternalRequest request,
                                                                 @AuthenticationPrincipal Jwt jwt) {

        BankStatement bankStatement = invoicePaymentService.payCurrentInvoice(new PayInvoiceInput(request.getAmount(), getUserIdFrom(jwt), null));

        return ResponseEntity.ok().body(PayInvoiceInternalResponse.fromBankStatement(bankStatement));
    }

    @PostMapping("/invoices/{invoiceId}")
    public ResponseEntity<PayInvoiceInternalResponse> payInvoiceById(@RequestBody @Valid PayInvoiceInternalRequest request,
                                                                     @PathVariable final String invoiceId,
                                                                     @AuthenticationPrincipal Jwt jwt) {

        BankStatement bankStatement = invoicePaymentService.payInvoiceById(new PayInvoiceInput(request.getAmount(), getUserIdFrom(jwt), invoiceId));

        return ResponseEntity.ok().body(PayInvoiceInternalResponse.fromBankStatement(bankStatement));
    }
}
