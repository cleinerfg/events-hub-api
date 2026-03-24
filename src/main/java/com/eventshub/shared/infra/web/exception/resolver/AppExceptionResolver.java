package com.eventshub.shared.infra.web.exception.resolver;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.AppException;
import com.eventshub.shared.infra.web.exception.support.ProblemDetailFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AppExceptionResolver {

    private final ProblemDetailFactory problemDetailFactory;

    public ProblemDetail resolve(AppException ex) {
        AppError error = ex.getError();

        var problemDetail = problemDetailFactory.create(error, ex.getMessage());
        Set<String> fails = ex.getFails();

        if (!fails.isEmpty()) problemDetail.setProperty("fails", fails);

        return problemDetail;
    }
}
