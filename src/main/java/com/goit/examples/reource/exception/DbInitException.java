package com.goit.examples.reource.exception;

public class DbInitException extends RuntimeException {
    public DbInitException() {
    }

    public DbInitException(String message) {
        super(message);
    }

    public DbInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
