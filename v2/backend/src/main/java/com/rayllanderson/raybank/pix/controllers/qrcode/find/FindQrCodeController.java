package com.rayllanderson.raybank.pix.controllers.qrcode.find;

import com.rayllanderson.raybank.pix.service.qrcode.find.FindQrCodeMapper;
import com.rayllanderson.raybank.pix.service.qrcode.find.FindQrCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "pix")
@RestController
@RequestMapping("api/v1/internal/pix/qrcode")
@RequiredArgsConstructor
public class FindQrCodeController {

    private final FindQrCodeMapper mapper;
    private final FindQrCodeService qrCodeService;

    @GetMapping("/{idOrCode}")
    public ResponseEntity<?> findById(@PathVariable String idOrCode, @AuthenticationPrincipal Jwt jwt) {

        final var response = qrCodeService.findByIdOrCode(idOrCode);

        return ResponseEntity.status(HttpStatus.OK).body(mapper.from(response));
    }
}
