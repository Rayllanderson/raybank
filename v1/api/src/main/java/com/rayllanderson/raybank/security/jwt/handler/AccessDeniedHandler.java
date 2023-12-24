package com.rayllanderson.raybank.security.jwt.handler;

import com.rayllanderson.raybank.security.jwt.ServletUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Classe chamada quando acontece o erro 403 - FORBIDDEN
 */
@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException {

        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {

            String json = ServletUtil.getJson("error", "Access denied");
            ServletUtil.write(response, HttpStatus.FORBIDDEN, json);
        }
    }
}
