package com.rayllanderson.raybank.pix.controllers.transfer;

import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.pix.service.transfer.PixTransferInput;
import com.rayllanderson.raybank.pix.service.transfer.PixTransferMapper;
import com.rayllanderson.raybank.pix.service.transfer.PixTransferService;
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
@RequestMapping("api/v1/internal/accounts/{accountId}/pix/transfer")
@RequiredArgsConstructor
public class PixTransferController {

    private final PixTransferMapper mapper;
    private final PixTransferService pixTransferService;

    @PostMapping
    @RequiredAccountOwner
    public ResponseEntity<PixTransferResponse> transfer(@RequestBody @Valid PixTransferRequest request,
                                                        @PathVariable String accountId,
                                                        @AuthenticationPrincipal Jwt jwt) {

        final var transfer = new PixTransferInput(accountId, request.getCreditKey(), request.getAmount(), request.getMessage());

        final var response = pixTransferService.transfer(transfer);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.from(response));
    }
}
