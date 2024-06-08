package com.rayllanderson.raybank.users.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePicture {
    @NotBlank
    private String key;
    @Lob
    @NotNull
    private String preSignedUrl;
    @NotNull
    private Instant expiration;

    public boolean isExpired() {
        if (expiration == null) {
            return true;
        }
        return Instant.now().isAfter(expiration);
    }
}
