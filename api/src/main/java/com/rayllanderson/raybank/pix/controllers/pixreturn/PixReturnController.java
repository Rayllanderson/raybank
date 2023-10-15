package com.rayllanderson.raybank.pix.controllers.pixreturn;

import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.pix.service.pixreturn.PixReturnMapper;
import com.rayllanderson.raybank.pix.service.pixreturn.PixReturnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/internal/accounts/{accountId}/pix/return")
@RequiredArgsConstructor
public class PixReturnController {

    private final PixReturnMapper mapper;
    private final PixReturnService service;

    @PostMapping
    @RequiredAccountOwner
    public ResponseEntity<PixReturnResponse> doReturn(@RequestBody @Valid PixReturnRequest request,
                                                      @PathVariable String accountId,
                                                      @AuthenticationPrincipal Jwt jwt) {
        final var response = service.doReturn(mapper.from(request, accountId));

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.from(response));
    }
}
