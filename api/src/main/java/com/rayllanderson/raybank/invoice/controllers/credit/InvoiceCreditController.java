package com.rayllanderson.raybank.invoice.controllers.credit;

import com.rayllanderson.raybank.invoice.services.credit.InvoiceCreditInput;
import com.rayllanderson.raybank.invoice.services.credit.InvoiceCreditService;
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
public class InvoiceCreditController {

    private final InvoiceCreditService invoiceCreditService;

    @RequiredAccountAndCardOwner
    @PostMapping("/invoices/current/pay")
    public ResponseEntity<InvoiceCreditResponse> payInvoice(@RequestBody @Valid InvoiceCreditRequest request,
                                                            @PathVariable String accountId,
                                                            @PathVariable String cardId,
                                                            @AuthenticationPrincipal Jwt jwt) {

        final var transaction = invoiceCreditService.creditCurrent(new InvoiceCreditInput(request.getAmount(), accountId, cardId, null));

        return ResponseEntity.ok().body(InvoiceCreditResponse.fromTransaction(transaction));
    }

    @RequiredAccountAndCardOwner
    @PostMapping("/invoices/{invoiceId}/pay")
    public ResponseEntity<InvoiceCreditResponse> payInvoiceById(@RequestBody @Valid InvoiceCreditRequest request,
                                                                @PathVariable String accountId,
                                                                @PathVariable String cardId,
                                                                @PathVariable final String invoiceId,
                                                                @AuthenticationPrincipal Jwt jwt) {

        final var transaction = invoiceCreditService.creditById(new InvoiceCreditInput(request.getAmount(), accountId, cardId, invoiceId));

        return ResponseEntity.ok().body(InvoiceCreditResponse.fromTransaction(transaction));
    }
}
