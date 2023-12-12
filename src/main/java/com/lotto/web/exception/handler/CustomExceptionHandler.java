package com.lotto.web.exception.handler;

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
}
