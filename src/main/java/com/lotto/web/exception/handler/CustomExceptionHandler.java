package com.lotto.web.exception.handler;

import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.AuthException;
import com.lotto.web.exception.custom.DuplicatedException;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class CustomExceptionHandler extends BaseExceptionHandler{

    @ExceptionHandler(NotFoundException.class)
    public Object handleNotFound(NotFoundException ex) {
        return toResponse(ex);
    }

    @ExceptionHandler(AuthException.class)
    public Object handleAuth(AuthException ex) {
        return toResponse(ex);
    }

    @ExceptionHandler(InvalidStateException.class)
    public Object handleInvalidState(InvalidStateException ex) {
        return toResponse(ex);
    }

    @ExceptionHandler(DuplicatedException.class)
    public Object handleDuplicated(DuplicatedException ex) {
        return toResponse(ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Object handleValidationViolation(ConstraintViolationException ex) {
        log.info("Parameter Error :: {}", ex.getConstraintViolations().size());
        ConstraintViolation<?> violation = ((ConstraintViolation<?>) ex.getConstraintViolations().toArray()[0]);
        String message = "< " + violation.getPropertyPath() + "> " + violation.getMessage();
        return toResponse(ErrorMessage.REQUEST_QUERY_PARAM, new String[] {message});
    }
}
