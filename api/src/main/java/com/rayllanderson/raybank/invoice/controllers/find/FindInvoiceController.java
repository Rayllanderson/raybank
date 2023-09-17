package com.rayllanderson.raybank.invoice.controllers.find;

import com.rayllanderson.raybank.invoice.services.find.FindInvoiceService;
import com.rayllanderson.raybank.security.RequiredCardOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/internal/credit-card")
@RequiredArgsConstructor
public class FindInvoiceController {

    private final FindInvoiceService findInvoiceService;

    @RequiredCardOwner
    @GetMapping("/{cardId}/invoices")
    public ResponseEntity<List<FindInvoiceResponse>> findAll(@PathVariable String cardId, @AuthenticationPrincipal Jwt jwt) {
        final var invoices = findInvoiceService.findAllByCardId(cardId);
        return ResponseEntity.ok(FindInvoiceResponse.map(invoices));
    }

    @RequiredCardOwner
    @GetMapping("/{cardId}/invoices/{invoiceId}")
    public ResponseEntity<FindInvoiceResponse> findAll(@PathVariable String cardId, @PathVariable String invoiceId, @AuthenticationPrincipal Jwt jwt) {
        final var invoice = findInvoiceService.findById(invoiceId);
        return ResponseEntity.ok(FindInvoiceResponse.from(invoice));
    }

}
