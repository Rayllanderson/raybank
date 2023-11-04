package com.rayllanderson.raybank.pix.controllers.pixreturn;

import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.core.security.method.RequiredPixOwner;
import com.rayllanderson.raybank.pix.service.pixreturn.PixReturnMapper;
import com.rayllanderson.raybank.pix.service.pixreturn.PixReturnService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "pix")
@RestController
@RequestMapping("api/v1/internal/pix/return")
@RequiredArgsConstructor
public class PixReturnController {

    private final PixReturnMapper mapper;
    private final PixReturnService service;

    @PostMapping
    @RequiredPixOwner
    public ResponseEntity<PixReturnResponse> doReturn(@RequestBody @Valid PixReturnRequest request,
                                                      @AuthenticationPrincipal Jwt jwt) {
        final var accountId = JwtUtils.getAccountIdFrom(jwt);

        final var response = service.doReturn(mapper.from(request, accountId));

        return ResponseEntity.ok().body(mapper.from(response));
    }
}
