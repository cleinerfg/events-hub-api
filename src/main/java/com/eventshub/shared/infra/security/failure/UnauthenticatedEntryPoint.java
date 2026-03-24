package com.eventshub.shared.infra.security.failure;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Bridges the Spring Security filter chain and the MVC exception handling layer.
 *
 * <p>This class is necessary to capture {@link org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint}
 * and specifically {@link org.springframework.security.authentication.InsufficientAuthenticationException},
 * which is thrown by Spring Security when a protected route is accessed without a token.
 *
 * <p>We cannot throw a "token required" error directly inside {@link com.eventshub.shared.infra.security.SecurityFilter}
 * for missing tokens, because the filter runs for every request — including public routes that
 * legitimately expect no token. Doing so would break those routes.
 *
 * <p>Instead, the filter allows unauthenticated requests to pass through. Spring Security then
 * intercepts the request at the authorization level and delegates to this entry point, which
 * forwards the exception to {@link com.eventshub.shared.infra.web.exception.RestExceptionHandler}
 * via the {@link org.springframework.web.servlet.HandlerExceptionResolver}.
 */

@Component
public class UnauthenticatedEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    // Lombok's @RequiredArgsConstructor doesn't work with @Qualifier — need a manual constructor.
    public UnauthenticatedEntryPoint(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException ex) {
        resolver.resolveException(request, response, null, ex);
    }
}