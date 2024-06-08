package com.rayllanderson.raybank.core.security.keycloak;

import com.rayllanderson.raybank.core.exceptions.RaybankException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    private static final String USER_ID_CLAIM = "sub";

    public static String getUserIdFrom(Jwt jwt) {
        if (jwt == null) {
            throw RaybankException.unauthorized();
        }
        return jwt.getClaim(USER_ID_CLAIM);
    }

    public static String getAccountIdFrom(Jwt jwt) {
        return getUserIdFrom(jwt);
    }
}
