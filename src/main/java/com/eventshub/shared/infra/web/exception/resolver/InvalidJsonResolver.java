package com.eventshub.shared.infra.web.exception.resolver;

import com.eventshub.shared.core.exception.AppException;
import com.eventshub.shared.infra.web.exception.support.ProblemDetailFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvalidJsonResolver {

    private final ProblemDetailFactory problemDetailFactory;

    public ProblemDetail resolve() {
        var invalidJson = AppException.invalidJson();
        return problemDetailFactory.create(
                invalidJson.getError(),
                invalidJson.getMessage()
        );
    }
}
