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
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo invalidArgumentHandle(final ConstraintViolationException e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorInfo("Validation error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo invalidArgumentHandle(final MethodArgumentNotValidException e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorInfo("Validation error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo missingRequestParameterHandle(final MissingServletRequestParameterException e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorInfo("Missing request parameter", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo illegalStateHandle(final IllegalStateException e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorInfo("Invalid arguments given", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo unexpectedErrorHandle(final Throwable e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return new ErrorInfo("Internal server error", e.getMessage());
    }

    public static class ErrorInfo {
        String error;
        String description;

        public ErrorInfo(String error, String description) {
            this.error = error;
            this.description = description;
        }
    }

}
