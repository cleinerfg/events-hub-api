package com.eventshub.infra.exception;

import com.eventshub.core.exception.DuplicateEventIdentifierException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DuplicateEventIdentifierException.class)
    public ProblemDetail handleDuplicateEventIdentifierException(DuplicateEventIdentifierException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, ex.getMessage()
        );
        problemDetail.setTitle("DUPLICATE_EVENT_IDENTIFIER");
        return problemDetail;
    }
}
