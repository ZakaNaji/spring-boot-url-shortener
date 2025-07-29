package com.znaji.urlshortener.web.exception;

import com.znaji.urlshortener.domain.exception.InvalidShortUrlKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidShortUrlKeyException.class)
    public String handleInvalidShortKey(InvalidShortUrlKeyException e) {
        log.warn("invalid url exception will redirect to 404 page", e);
        return "error/404";
    }
}
