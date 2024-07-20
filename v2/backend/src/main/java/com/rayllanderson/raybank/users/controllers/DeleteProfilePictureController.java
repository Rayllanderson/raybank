package com.rayllanderson.raybank.users.controllers;

import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.core.services.S3UploadOutput;
import com.rayllanderson.raybank.users.controllers.validation.ValidFileSize;
import com.rayllanderson.raybank.users.controllers.validation.ValidFileType;
import com.rayllanderson.raybank.users.services.profile.DeleteProfilePictureService;
import com.rayllanderson.raybank.users.services.profile.UploadProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/internal/")
@RequiredArgsConstructor
@Validated
public class DeleteProfilePictureController {

    private final DeleteProfilePictureService deleteProfilePictureService;

    @RequiredAccountOwner
    @DeleteMapping("accounts/{accountId}/profile-picture")
    public ResponseEntity<?> delete(@PathVariable String accountId,
                                    @AuthenticationPrincipal Jwt jwt) throws IOException {

        deleteProfilePictureService.delete(accountId);

        return ResponseEntity.noContent().build();
    }
}
