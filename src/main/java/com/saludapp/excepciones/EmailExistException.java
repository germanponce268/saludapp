package com.saludapp.excepciones;

public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }
}
