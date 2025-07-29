package com.znaji.urlshortener.domain.exception;

public class InvalidShortUrlKeyException extends RuntimeException {
    public InvalidShortUrlKeyException(String s) {
        super(s);
    }
}
