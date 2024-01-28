package com.rayllanderson.raybank.users.services.register;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class RegisterUtils {

    public static String getIdFromResourcePath(final String resourcePath) {
        return resourcePath.split("/")[1];
    }
}
