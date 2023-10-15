package com.rayllanderson.raybank.pix.controllers.qrcode;

import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.pix.service.qrcode.GenerateQrCodeInput;
import com.rayllanderson.raybank.pix.service.qrcode.GenerateQrCodeMapper;
import com.rayllanderson.raybank.pix.service.qrcode.GenerateQrCodeService;
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
@RequestMapping("api/v1/internal/accounts/{accountId}/pix/qrcode")
@RequiredArgsConstructor
public class GenerateQrCodeController {

    private final GenerateQrCodeMapper mapper;
    private final GenerateQrCodeService service;

    @PostMapping
    @RequiredAccountOwner
    public ResponseEntity<GenerateQrCodeResponse> transfer(@RequestBody @Valid GenerateQrCodeRequest request,
                                                           @PathVariable String accountId,
                                                           @AuthenticationPrincipal Jwt jwt) {

        final var qrCodeInput = new GenerateQrCodeInput(request.getAmount(), accountId, request.getDescription());

        final var response = service.generate(qrCodeInput);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.from(response));
    }
}
