package com.rayllanderson.raybank.users.controllers;

import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.users.services.profile.ProfilePictureOutput;
import com.rayllanderson.raybank.users.services.profile.FindProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/internal/")
@RequiredArgsConstructor
public class GetProfilePictureController {

    private final FindProfileService findProfileService;

    @RequiredAccountOwner
    @GetMapping("accounts/{accountId}/profile-picture")
    public ResponseEntity<?> findImageById(@PathVariable String accountId, @AuthenticationPrincipal Jwt jwt) {

        Optional<ProfilePictureOutput> profileOutputOptional = findProfileService.findByUserId(accountId);

        if (profileOutputOptional.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Profile picture is expired. Please renew"), HttpStatus.GONE);
        }

        return ResponseEntity.ok().body(profileOutputOptional.get());
    }
}
