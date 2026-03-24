package com.eventshub.shared.infra.security;

import com.eventshub.shared.core.security.TokenException;
import com.eventshub.shared.core.security.TokenPayload;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextService {

    public TokenPayload getAuthenticatedPayload() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            throw TokenException.required();
        }

        return (TokenPayload) auth.getPrincipal();
    }
}
