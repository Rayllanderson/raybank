package com.rayllanderson.raybank.pix.controllers.qrcode.generate;

import com.rayllanderson.raybank.core.security.method.RequiredPixKeyOwner;
import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeInput;
import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeMapper;
import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeService;
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
@RequestMapping("api/v1/internal/pix/qrcode")
@RequiredArgsConstructor
public class GenerateQrCodeController {

    private final GenerateQrCodeMapper mapper;
    private final GenerateQrCodeService service;

    @PostMapping
    @RequiredPixKeyOwner
    public ResponseEntity<GenerateQrCodeResponse> generate(@RequestBody @Valid GenerateQrCodeRequest request,
                                                           @AuthenticationPrincipal Jwt jwt) {

        final var qrCodeInput = new GenerateQrCodeInput(request.getAmount(), request.getCreditKey(), request.getDescription());

        final var response = service.generate(qrCodeInput);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.from(response));
    }
}
