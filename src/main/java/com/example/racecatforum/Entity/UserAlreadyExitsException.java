package com.example.racecatforum.Entity;

public class UserAlreadyExitsException extends Exception {
    public UserAlreadyExitsException() {
    }

    public UserAlreadyExitsException(String message) {
        super(message);
    }
}
