package com.rayllanderson.raybank.external.token;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;

@Entity
public class ExternalToken {

    @Id
    @NotEmpty
    private String token;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String clientName;

    @Column(nullable = false)
    private LocalDate creationTime;

    @Transient
    private static final SecureRandom secureRandom = new SecureRandom();
    @Transient
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public ExternalToken(String clientName) {
        this.clientName = clientName;
        this.token = generateToken();
        this.creationTime = LocalDate.now();
    }

    @Deprecated(since = "0.0.1")
    public ExternalToken() {
    }

    public String getToken() {
        return token;
    }

    public String getClientName() {
        return clientName;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    private String generateToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
