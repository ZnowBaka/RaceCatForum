package com.example.racecatforum.Entity;

public class UserDoesNotExistsException extends RuntimeException {

    public UserDoesNotExistsException() {}

    public UserDoesNotExistsException(String message) {
        super(message);
    }
}
