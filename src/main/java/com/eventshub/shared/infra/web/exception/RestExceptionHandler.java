package com.eventshub.shared.infra.web.exception;

import com.eventshub.shared.core.exception.AppException;
import com.eventshub.shared.infra.web.exception.resolver.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RequiredArgsConstructor
@RestControllerAdvice
public class RestExceptionHandler {

    private final InvalidJsonResolver invalidJsonResolver;
    private final InvalidParamResolver invalidParamResolver;
    private final ValidationFailResolver validationFailResolver;
    private final AppExceptionResolver appExceptionResolver;
    private final InsufficientAuthenticationResolver insufficientAuthenticationResolver;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return invalidJsonResolver.resolve(ex);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return invalidParamResolver.resolve(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return validationFailResolver.resolve(ex);
    }

    @ExceptionHandler(AppException.class)
    public ProblemDetail handleAppException(AppException ex) {
        return appExceptionResolver.resolve(ex);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ProblemDetail handleInsufficientAuthenticationException() {
        return insufficientAuthenticationResolver.resolve();
    }
}
