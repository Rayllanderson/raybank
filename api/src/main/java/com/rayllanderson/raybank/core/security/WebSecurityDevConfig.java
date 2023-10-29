package com.rayllanderson.raybank.core.security;

import com.rayllanderson.raybank.core.security.keycloak.KeycloakRealmRoleConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Profile("!prod")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityDevConfig {

    private static final String ROLE_USER = "USER";
    private static final String ROLE_ESTABLISMENT = "ESTABLISHMENT";
    private static final String ROLE_ESTABLISMENT_REGISTER = "ESTABLISHMENT_REGISTER";
    private static final String ROLE_USER_REGISTER = "USER_REGISTER";

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
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/users/register")).hasRole(ROLE_USER_REGISTER)
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/establishments/register")).hasRole(ROLE_ESTABLISMENT_REGISTER)
                        .requestMatchers(antMatcher("/api/v1/internal/**")).hasRole(ROLE_USER)
                        .requestMatchers(antMatcher("/api/v1/external/**")).hasRole(ROLE_ESTABLISMENT)
                        .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(antMatcher("/v3/api-docs/**")).permitAll()
                        .anyRequest().authenticated())
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
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
}
