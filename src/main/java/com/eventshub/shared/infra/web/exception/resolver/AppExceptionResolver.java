package com.eventshub.shared.infra.web.exception.resolver;

import com.eventshub.shared.core.exception.AppException;
import com.eventshub.shared.infra.web.exception.support.ProblemDetailFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppExceptionResolver {

    private final ProblemDetailFactory problemDetailFactory;

    public ProblemDetail resolve(AppException ex) {
        var error = ex.getError();
        return problemDetailFactory.create(error, ex.getMessage());
    }
}
