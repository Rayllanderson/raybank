package com.rayllanderson.raybank.pix.model;

import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.model.key.PixKeyType;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class PixKeyData {
    private String key;
    private String name;
    private PixKeyType type;
    private LocalDateTime createdAt;
    private String accountId;

    public static PixKeyData from(PixKey key) {
        return new PixKeyData(key.getKey(), key.getName(), key.getType(), key.getCreatedAt(), key.getAccountId());
    }

    public boolean sameAccount(final String accountId) {
        return this.getAccountId().equals(accountId);
    }
}
