package com.eventshub.shared.infra.web.exception.resolver;

import com.eventshub.modules.user.core.exception.InvalidPasswordException;
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

        if (ex instanceof InvalidPasswordException invalidPasswordEx) {
            Set<String> fails = invalidPasswordEx.getFails();
            problemDetail.setProperty("fails", fails);
        }

        return problemDetail;
    }
}
