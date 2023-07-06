package com.ingsoftware.contacts.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("User with that email already exists.");
    }
}
