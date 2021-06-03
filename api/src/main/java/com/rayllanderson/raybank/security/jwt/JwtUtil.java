package com.rayllanderson.raybank.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class JwtUtil {
    // Chave com algoritmo HS512
    // http://www.allkeysgenerator.com
    private static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

    public static Claims getClaims(String token) {
        byte[] signingKey = JwtUtil.JWT_SECRET.getBytes();

        token = token.replace("Bearer ", "");

        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token).getBody();
    }

    public static String getLogin(String token) {
        Claims claims = getClaims(token);
        if (!isNull(claims)) {
            return claims.getSubject();
        }
        return null;
    }

    public static List<GrantedAuthority> getRoles(String token) {
        Claims claims = getClaims(token);
        if (claims == null) {
            return null;
        }
        return ((List<?>) claims
                .get("rol")).stream()
                .map(authority -> new SimpleGrantedAuthority((String) authority))
                .collect(Collectors.toList());
    }

    public static boolean isTokenValid(String token) {
        Claims claims = getClaims(token);
        if (nonNull(claims)) {
            String login = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return login != null && expiration != null && now.before(expiration);
        }
        return false;
    }

    public static String createToken(UserDetails user) {
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKey = JwtUtil.JWT_SECRET.getBytes();

        long days = 1000;
        long time = days * 24 /*horas*/ * 60 /*min*/ * 60 /*seg*/ * 1000  /*millis*/;
        Date expiration = new Date(System.currentTimeMillis() + time);

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .claim("rol", roles)
                .compact();
    }
}
