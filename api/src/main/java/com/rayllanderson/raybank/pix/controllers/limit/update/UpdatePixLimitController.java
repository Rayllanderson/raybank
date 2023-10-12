package com.rayllanderson.raybank.pix.controllers.limit.update;

import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.pix.service.limit.update.UpdatePixLimitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/internal/accounts/{accountId}/pix/limit")
@RequiredArgsConstructor
public class UpdatePixLimitController {

    private final UpdatePixLimitService service;


    @PatchMapping
    @RequiredAccountOwner
    public ResponseEntity<?> update(@Valid @RequestBody UpdatePixLimitRequest request,
                                                        @PathVariable String accountId,
                                                        @AuthenticationPrincipal Jwt jwt) {

        service.update(request.getNewLimit(), accountId);

        return ResponseEntity.ok().body(Map.of("new_limit", request.getNewLimit()));
    }
}
