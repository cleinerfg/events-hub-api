package com.eventshub.event.infra.exception;

import com.eventshub.event.core.exception.DuplicateIdentifierException;
import com.eventshub.event.core.exception.InvalidPeriodException;
import com.eventshub.event.core.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(SystemIntegrityException.class)
    public ProblemDetail handleSystemIntegrityException(SystemIntegrityException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
        );
        problemDetail.setTitle("SYSTEM_INTEGRITY_ERROR");
        return problemDetail;
    }

    @ExceptionHandler(DuplicateIdentifierException.class)
    public ProblemDetail handleDuplicateEventIdentifierException(DuplicateIdentifierException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, ex.getMessage()
        );
        problemDetail.setTitle("DUPLICATE_EVENT_IDENTIFIER");
        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage()
        );
        problemDetail.setTitle("NOT_FOUND_EVENT");
        return problemDetail;
    }

    @ExceptionHandler(InvalidPeriodException.class)
    public ProblemDetail handleInvalidPeriodException(InvalidPeriodException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage()
        );
        problemDetail.setTitle("INVALID_PERIOD");
        return problemDetail;
    }
}
