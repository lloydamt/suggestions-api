package com.lamt.suggestionsapi.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " already exists");
    }
}
