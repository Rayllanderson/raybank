package com.rayllanderson.raybank.core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final Environment env;
    private static final String ROLE_USER = "USER";
    private static final String ROLE_ESTABLISMENT = "ESTABLISMENT";
    private static final String ROLE_ESTABLISMENT_REGISTER = "ESTABLISMENT_REGISTER";

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .requestCache(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/users/register")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/establishments/register")).hasRole(ROLE_ESTABLISMENT_REGISTER)
                        .requestMatchers(antMatcher("/api/v1/users/authenticated/*")).hasRole(ROLE_USER)
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/external/payments/card")).hasRole(ROLE_ESTABLISMENT)
                        .anyRequest().authenticated())
                .headers(headers -> {
                    //todo:: remover
                    if (Arrays.asList(env.getActiveProfiles()).contains("local"))
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                    else
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
                .oauth2ResourceServer(oauth -> {
                    oauth.jwt(jwt ->
                            jwt.jwtAuthenticationConverter(jwtKeycloakAuthenticationConverter())
                    );
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .build();
    }

    /**
     * Por padrão o JWT converter não consegue pegar as roles que vem do Keycloak,
     * por isso estamos utilizando um converter customizado
     */
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtKeycloakAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        return jwtConverter;
    }

    @SuppressWarnings("unchecked")
    private static class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
            return ((List<String>) realmAccess.get("roles")).stream()
                    .map(roleName -> "ROLE_" + roleName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
    }
}
