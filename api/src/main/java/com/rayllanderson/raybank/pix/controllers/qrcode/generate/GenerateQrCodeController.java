package com.rayllanderson.raybank.pix.controllers.qrcode.generate;

import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeInput;
import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeMapper;
import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeService;
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

@RestController
@RequestMapping("api/v1/external/pix/qrcode")
@RequiredArgsConstructor
public class GenerateQrCodeController {

    private final GenerateQrCodeMapper mapper;
    private final GenerateQrCodeService service;

    @PostMapping
    public ResponseEntity<GenerateQrCodeResponse> generate(@RequestBody @Valid GenerateQrCodeRequest request,
                                                           @AuthenticationPrincipal Jwt jwt) {

        final var qrCodeInput = new GenerateQrCodeInput(request.getAmount(), request.getCreditKey(), request.getDescription());

        final var response = service.generate(qrCodeInput);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.from(response));
    }
}
