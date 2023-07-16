package com.example.taskadserver.exceptions;

public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException(String type, String attribute, String value) {
        super(String.format("%s with %s %s already exists.", type, attribute, value));
    }
}
