package com.eventshub.shared.infra.web.exception.resolver;

import com.eventshub.shared.infra.web.exception.resolver.handler.InvalidQueryParamsHandler;
import com.eventshub.shared.infra.web.exception.resolver.handler.ValidationFailHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RequiredArgsConstructor
@Component
public class MethodArgumentNotValidResolver {

    private final ValidationFailHandler validationFailHandler;
    private final InvalidQueryParamsHandler invalidQueryParamsHandler;

    public ProblemDetail resolve(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getBindingResult().getFieldErrors();

        var queryParamsFailProblemDetail = invalidQueryParamsHandler.handle(fieldErrors);
        var validationFailProblemDetail = validationFailHandler.handle(fieldErrors);

        return queryParamsFailProblemDetail == null ?
                validationFailProblemDetail :
                queryParamsFailProblemDetail;
    }

}
