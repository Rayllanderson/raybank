package com.rayllanderson.raybank.pix.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PixKeyUtils {

    private static final String EMPTY = "";

    public static String removeSpecialCharacters(final String key) {
        return key.replace("(", EMPTY)
                .replace(")", EMPTY)
                .replace("-", EMPTY)
                .replace(" ", EMPTY)
                .replace(".", EMPTY);
    }
}
