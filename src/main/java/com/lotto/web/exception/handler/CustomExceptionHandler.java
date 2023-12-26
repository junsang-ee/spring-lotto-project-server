package com.lotto.web.exception.handler;

import com.lotto.web.exception.custom.AuthException;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
