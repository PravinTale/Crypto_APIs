package com.koinxApplication.exceptions;

/**
 * The type Crypto not found exception.
 */
public class CryptoNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Crypto not found exception.
     *
     * @param message the message
     */
    public CryptoNotFoundException(String message) {
        super(message);
    }
}
