package com.example.citronix.web.errors.exceptions;

public class FarmAlreadyExistException extends RuntimeException {
    public FarmAlreadyExistException(String message) {
        super(message);
    }
}
