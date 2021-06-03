package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.pix.PixPostDto;
import com.rayllanderson.raybank.dtos.requests.pix.PixPutDto;
import com.rayllanderson.raybank.dtos.responses.pix.PixPostResponse;
import com.rayllanderson.raybank.dtos.responses.pix.PixResponseDto;
import com.rayllanderson.raybank.models.Pix;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.services.PixService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users/authenticated/pix")
@RequiredArgsConstructor
public class PixController {

    private final PixService pixService;

    @PostMapping
    public ResponseEntity<PixPostResponse> save(@RequestBody @Valid PixPostDto pixPostDto,
                                                @AuthenticationPrincipal User authenticatedUser) {
        pixPostDto.setOwnerId(authenticatedUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(pixService.register(pixPostDto));
    }

    @GetMapping
    public ResponseEntity<PixResponseDto> findAll(@AuthenticationPrincipal User authenticatedUser) {
        return ResponseEntity.ok(pixService.findAllFromUser(authenticatedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pix> findById(@PathVariable Long id, @AuthenticationPrincipal User authenticatedUser) {
        return ResponseEntity.ok(pixService.findById(id, authenticatedUser));
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid PixPutDto pixPutDto, @AuthenticationPrincipal User authenticatedUser) {
        pixPutDto.setOwnerId(authenticatedUser.getId());
        pixService.update(pixPutDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User authenticatedUser) {
        pixService.deleteById(id, authenticatedUser);
        return ResponseEntity.noContent().build();
    }
}
