package com.rayllanderson.raybank.e2e.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithEstablishmentUserSecurityContextFactory implements WithSecurityContextFactory<WithEstablishmentUser> {

    @Override
    public SecurityContext createSecurityContext(WithEstablishmentUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Jwt principal = Jwt.withTokenValue("eyJhbGciOiJSUzI1NiIsInR5cCIgOi")
                .header("", "")
                .claim("sub", customUser.id())
                .build();

        Authentication auth = UsernamePasswordAuthenticationToken.authenticated(principal, "password", List.of(new SimpleGrantedAuthority("ROLE_ESTABLISHMENT"), new SimpleGrantedAuthority("ROLE_USER")));

        context.setAuthentication(auth);

        return context;
    }
}