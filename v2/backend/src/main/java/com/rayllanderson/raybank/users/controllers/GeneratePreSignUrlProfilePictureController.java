package com.rayllanderson.raybank.users.controllers;

import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.core.services.S3PresignUrlOutput;
import com.rayllanderson.raybank.users.services.profile.GeneratePreSignUrlProfilePictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal/")
@RequiredArgsConstructor
public class GeneratePreSignUrlProfilePictureController {

    private final GeneratePreSignUrlProfilePictureService generatePreSignUrlService;

    @RequiredAccountOwner
    @PostMapping("accounts/{accountId}/renew-profile-picture")
    public ResponseEntity<?> generatePreSignUrl(@PathVariable String accountId, @AuthenticationPrincipal Jwt jwt) {

        S3PresignUrlOutput s3PresignUrlOutput = generatePreSignUrlService.generate(accountId);

        return ResponseEntity.ok(ProfilePictureResponse.from(s3PresignUrlOutput));
    }
}
