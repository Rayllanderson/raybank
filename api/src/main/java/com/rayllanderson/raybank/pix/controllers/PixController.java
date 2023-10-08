package com.rayllanderson.raybank.pix.controllers;

import com.rayllanderson.raybank.pix.controllers.requests.PixPostDto;
import com.rayllanderson.raybank.pix.controllers.requests.PixPutDto;
import com.rayllanderson.raybank.pix.controllers.responses.PixPostResponse;
import com.rayllanderson.raybank.pix.controllers.responses.PixResponseDto;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.pix.service.PixService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/authenticated/pix")
@RequiredArgsConstructor
public class PixController {

    private final PixService pixService;

    @PostMapping
    public ResponseEntity<PixPostResponse> save(@RequestBody @Valid PixPostDto pixPostDto,
                                                @AuthenticationPrincipal Jwt jwt) {
        pixPostDto.setOwnerId(JwtUtils.getUserIdFrom(jwt));
        return ResponseEntity.status(HttpStatus.CREATED).body(pixService.register(pixPostDto));
    }

    @GetMapping
    public ResponseEntity<List<PixResponseDto>> findAll(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(pixService.findAllFromUserId(JwtUtils.getUserIdFrom(jwt)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pix> findById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(pixService.findById(id, JwtUtils.getUserIdFrom(jwt)));
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid PixPutDto pixPutDto, @AuthenticationPrincipal Jwt jwt) {
        pixPutDto.setOwnerId(JwtUtils.getUserIdFrom(jwt));
        pixService.update(pixPutDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        pixService.deleteById(id, JwtUtils.getUserIdFrom(jwt));
        return ResponseEntity.noContent().build();
    }
}
