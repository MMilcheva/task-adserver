package com.example.taskadserver.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }


    public EntityNotFoundException(String type, Long id) {
        this(type, "id", String.valueOf(id));
    }

    public EntityNotFoundException(String type, String attribute, String value) {
        super(String.format("%s with %s %s not found.", type, attribute, value));
    }

    public EntityNotFoundException(String type, String attribute) {
        super(String.format("%s with %s already exists.", type, attribute));
    }

}

