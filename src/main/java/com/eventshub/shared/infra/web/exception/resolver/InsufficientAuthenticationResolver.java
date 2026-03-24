package com.eventshub.shared.infra.web.exception.resolver;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.security.TokenException;
import com.eventshub.shared.infra.web.exception.support.ProblemDetailFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsufficientAuthenticationResolver {

    private final ProblemDetailFactory problemDetailFactory;

    public ProblemDetail resolve() {
        var tokenException = TokenException.required();
        AppError error = tokenException.getError();
        return problemDetailFactory.create(error, tokenException.getMessage());

    }
}
