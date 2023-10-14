package com.rayllanderson.raybank.pix.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class E2EIdGenerator {

    private static final int RAYBANK_INSTITUATION_CODE = 13971290;
    private static final String E2E_INITIAL = "E";

    public static String generateE2E(LocalDateTime occuredOn) {
        final String dateToString = occuredOn.toLocalDate().toString().replace("-", "");
        final var randomStrings = randomStrings().toUpperCase();
        return E2E_INITIAL + RAYBANK_INSTITUATION_CODE + dateToString + randomStrings;
    }

    private static String randomStrings() {
        final String[] randomStrings = UUID.randomUUID().toString().split("-");
        return randomStrings[0] + randomStrings[1] + randomStrings[2].substring(0, randomStrings[2].length() -1);
    }
}
