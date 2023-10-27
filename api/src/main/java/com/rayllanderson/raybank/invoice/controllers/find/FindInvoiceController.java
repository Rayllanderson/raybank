package com.rayllanderson.raybank.invoice.controllers.find;

import com.rayllanderson.raybank.invoice.services.find.FindInvoiceMapper;
import com.rayllanderson.raybank.invoice.services.find.FindInvoiceService;
import com.rayllanderson.raybank.core.security.method.RequiredAccountAndCardOwner;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "invoices")
@RestController
@RequestMapping("api/v1/internal/accounts/{accountId}/cards/{cardId}")
@RequiredArgsConstructor
public class FindInvoiceController {

    private final FindInvoiceService findInvoiceService;
    private final FindInvoiceMapper mapper;

    @RequiredAccountAndCardOwner
    @GetMapping("/invoices")
    public ResponseEntity<List<FindInvoiceListResponse>> findAll(@PathVariable String accountId,
                                                             @PathVariable String cardId,
                                                             @AuthenticationPrincipal Jwt jwt) {
        final var invoices = findInvoiceService.findAllByCardId(cardId);
        return ResponseEntity.ok(mapper.fromOutput(invoices));
    }

    @RequiredAccountAndCardOwner
    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<FindInvoiceResponse> findAll(@PathVariable String accountId,
                                                       @PathVariable String cardId,
                                                       @PathVariable String invoiceId,
                                                       @AuthenticationPrincipal Jwt jwt) {
        final var invoice = findInvoiceService.findById(invoiceId);
        return ResponseEntity.ok(mapper.fromOutput(invoice));
    }

}
