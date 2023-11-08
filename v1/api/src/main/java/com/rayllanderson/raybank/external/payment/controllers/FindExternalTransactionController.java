package com.rayllanderson.raybank.external.payment.controllers;

import com.rayllanderson.raybank.external.payment.responses.ExternalTransactionDetailsResponse;
import com.rayllanderson.raybank.external.payment.services.FindExternalTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/external/payments")
@RequiredArgsConstructor
public class FindExternalTransactionController {

    private final FindExternalTransactionService externalTransactionService;

    @GetMapping("/{id}")
    public ResponseEntity<ExternalTransactionDetailsResponse> findExternalTransactionById(@PathVariable String id, @RequestHeader String token) {
        return ResponseEntity.ok(externalTransactionService.findById(id, token));
    }
}
