package com.lamt.suggestionsapi.exception;

public class UserNotPermittedException extends RuntimeException {
    public UserNotPermittedException() {
        super("You do not have the permission to do this");
    }
}
