package com.rayllanderson.raybank.users.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePicture {
    private String key;
    @Lob
    private String preSignedUrl;
    private LocalDateTime expiresIn;
}
