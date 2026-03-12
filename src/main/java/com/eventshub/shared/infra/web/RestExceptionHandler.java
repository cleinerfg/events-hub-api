package com.eventshub.shared.infra.web;

import com.eventshub.shared.core.exception.SystemIntegrityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class RestExceptionHandler {
    @ExceptionHandler(SystemIntegrityException.class)
    public ProblemDetail handleSystemIntegrityException(SystemIntegrityException ex) {
        var problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
        );
        problemDetail.setTitle("SYSTEM_INTEGRITY_ERROR");
        return problemDetail;
    }

    private
}
