package com.rayllanderson.raybank.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        boolean hasNoToken = !StringUtils.hasLength(token) || !token.startsWith("Bearer ");
        if (hasNoToken) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            if(! JwtUtil.isTokenValid(token)) {
                throw new AccessDeniedException("Acesso negado.");
            }

            String login = JwtUtil.getLogin(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(login);

            List<GrantedAuthority> authorities = JwtUtil.getRoles(token);

            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

            // Salva o Authentication no contexto do Spring
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);

        } catch (RuntimeException ex) {
            logger.error("Authentication error: " + ex.getMessage(),ex);
            throw ex;
        }
    }
}
