package com.eventshub.shared.infra.security;

import com.eventshub.shared.core.exception.AppException;
import com.eventshub.shared.core.security.TokenPayload;
import com.eventshub.shared.core.security.TokenPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenPort tokenPort;
    private final HandlerExceptionResolver resolver;

    // Lombok's @RequiredArgsConstructor doesn't work with @Qualifier — need a manual constructor.
    public SecurityFilter(
            TokenPort tokenPort,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.tokenPort = tokenPort;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String token = extractToken(request);

        if (token != null) {
            try {
                TokenPayload tokenPayload = tokenPort.validate(token);

                var authToken = new UsernamePasswordAuthenticationToken(
                        tokenPayload,
                        null,
                        Collections.emptyList()
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (AppException e) {
                resolver.resolveException(request, response, null, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (Strings.isNotEmpty(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}