package com.rayllanderson.raybank.users.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    private String id;
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    @NotBlank
    @Size(min = 3, max = 100)
    @Column(unique = true)
    private String username;
    private String email;
    private String authorities;
    @Enumerated(EnumType.STRING)
    private UserType type;
    private String bankAccountId;
    @Embedded
    private ProfilePicture profilePicture;

    public User(String id, String name, String username, String authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.authorities = authorities;
    }

    public boolean isEstablishment() {
        return this.type.equals(UserType.ESTABLISHMENT);
    }

    public static User fromId(final String id) {
        return User.builder().id(id).build();
    }

    public void addBankAccount(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public void updateName(final String name) {
        this.name = name;
    }

    public boolean hasProfilePicture() {
        return profilePicture != null && profilePicture.getKey() != null;
    }

    public boolean doesNotHaveProfilePicture() {
        return !hasProfilePicture();
    }

    public String getProfilePictureKey() {
        if (profilePicture == null)
            return null;
        return profilePicture.getKey();
    }

    public void addProfilePicture(final ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void updateProfilePicturePreSignedUrl(final String preSignedUrl, String thumbnailPreSignedUrl, Instant newExpiration) {
        if (this.profilePicture == null) return;
        this.profilePicture.setPreSignedUrl(preSignedUrl);
        this.profilePicture.setExpiration(newExpiration);
        this.profilePicture.setThumbnailPreSignedUrl(thumbnailPreSignedUrl);
    }

    public void updateThumbnail(final String thumbnailKey, final String preSignedUrl) {
        if (this.profilePicture == null) return;
        this.profilePicture.setThumbnailKey(thumbnailKey);
        this.profilePicture.setThumbnailPreSignedUrl(preSignedUrl);
    }

    public void deleteProfilePicture() {
        this.profilePicture = null;
    }
}
