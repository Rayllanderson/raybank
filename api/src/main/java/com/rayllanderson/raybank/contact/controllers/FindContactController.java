package com.rayllanderson.raybank.contact.controllers;

import com.rayllanderson.raybank.contact.service.find.FindContactMapper;
import com.rayllanderson.raybank.contact.service.find.FindContactService;
import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.core.security.method.RequiredContactOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/internal/accounts/{accountId}/contacts")
@RequiredArgsConstructor
public class FindContactController {

    private final FindContactMapper mapper;
    private final FindContactService service;

    @RequiredContactOwner
    @GetMapping("/{contactId}")
    public ResponseEntity<?> findById(@PathVariable String accountId,
                                      @PathVariable String contactId,
                                      @AuthenticationPrincipal Jwt jwt) {

        final var output = service.findById(contactId);
        final var response = mapper.from(output);
        return ResponseEntity.ok(response);
    }

    @RequiredAccountOwner
    @GetMapping
    public ResponseEntity<?> findAllByOwnerId(@PathVariable String accountId, @AuthenticationPrincipal Jwt jwt) {
        final var output = service.findAllByOwnerId(accountId);
        final var response = mapper.fromOutput(output);
        return ResponseEntity.ok(response);
    }
}
