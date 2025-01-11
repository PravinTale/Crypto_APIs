package com.koinxApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Data not found exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException{
    /**
     * Instantiates a new Data not found exception.
     *
     * @param message the message
     */
    public DataNotFoundException(String message) {
        super(message);
    }
}
