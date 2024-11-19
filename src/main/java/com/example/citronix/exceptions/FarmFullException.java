package com.example.citronix.exceptions;

public class FarmFullException extends RuntimeException {
    public FarmFullException(String message) {
        super(message);
    }
}
