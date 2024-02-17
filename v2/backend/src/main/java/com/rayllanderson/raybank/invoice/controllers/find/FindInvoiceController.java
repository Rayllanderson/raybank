package com.rayllanderson.raybank.invoice.controllers.find;

import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.core.security.method.RequiredInvoiceOwner;
import com.rayllanderson.raybank.invoice.services.find.FindInvoiceMapper;
import com.rayllanderson.raybank.invoice.services.find.FindInvoiceService;
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
@RequestMapping("api/v1/internal/accounts/{accountId}")
@RequiredArgsConstructor
public class FindInvoiceController {

    private final FindInvoiceService findInvoiceService;
    private final FindInvoiceMapper mapper;

    @RequiredAccountOwner
    @GetMapping("/invoices")
    public ResponseEntity<List<FindInvoiceListResponse>> findAll(@PathVariable String accountId,
                                                                 @AuthenticationPrincipal Jwt jwt) {
        final var invoices = findInvoiceService.findAllByAccountId(accountId);
        return ResponseEntity.ok(mapper.fromOutput(invoices));
    }

    @RequiredAccountOwner
    @GetMapping("/invoices/current")
    public ResponseEntity<?> findCurrentInvoice(@PathVariable String accountId,
                                                @AuthenticationPrincipal Jwt jwt) {
        final var invoice = findInvoiceService.findCurrentAccountId(accountId);
        return ResponseEntity.ok(mapper.fromOutput(invoice));
    }

    @RequiredInvoiceOwner
    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<FindInvoiceResponse> findAll(@PathVariable String accountId,
                                                       @PathVariable String invoiceId,
                                                       @AuthenticationPrincipal Jwt jwt) {
        final var invoice = findInvoiceService.findById(invoiceId);
        return ResponseEntity.ok(mapper.fromOutput(invoice));
    }

}
