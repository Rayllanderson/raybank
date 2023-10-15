package com.rayllanderson.raybank.pix.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class E2EIdGenerator {

    private static final int RAYBANK_INSTITUATION_CODE = 13971290;
    private static final String E2E_INITIAL = "E";
    private static final String D2D_INITIAL = "D";

    public static String generateE2E(LocalDateTime occuredOn) {
        return generate(occuredOn, E2E_INITIAL);
    }

    public static String generateD2D(LocalDateTime occuredOn) {
        return generate(occuredOn, D2D_INITIAL);
    }

    private static String generate(LocalDateTime occuredOn, String typeInitial) {
        final String dateToString = occuredOn.toLocalDate().toString().replace("-", "");
        final var randomStrings = randomStrings().toUpperCase();
        return typeInitial + RAYBANK_INSTITUATION_CODE + dateToString + randomStrings;
    }


    private static String randomStrings() {
        final String[] randomStrings = UUID.randomUUID().toString().split("-");
        return randomStrings[0] + randomStrings[1] + randomStrings[2].substring(0, randomStrings[2].length() -1);
    }
}
