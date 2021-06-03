package com.rayllanderson.raybank.security.jwt.handler;

import com.rayllanderson.raybank.security.jwt.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnauthorizedHandler.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        LOGGER.warn("UnauthorizedHandler, exception: " + authException);

        // Chamado se token errado ou ausente
        String json = ServletUtil.getJson("error", "Not authorized");
        ServletUtil.write(response, HttpStatus.FORBIDDEN, json);
    }
}
