package ru.practicum.stats.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidArgumentHandle(final ConstraintViolationException e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorResponse("Validation error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidArgumentHandle(final MethodArgumentNotValidException e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorResponse("Validation error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse missingRequestParameterHandle(final MissingServletRequestParameterException e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorResponse("Missing request parameter", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalStateHandle(final IllegalStateException e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorResponse("Invalid arguments given", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse unexpectedErrorHandle(final Throwable e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorResponse("An unexpected error has occurred", e.getMessage());
    }

}
