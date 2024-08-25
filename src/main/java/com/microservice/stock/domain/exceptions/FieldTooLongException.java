package com.microservice.stock.domain.exceptions;

public class FieldTooLongException extends RuntimeException {
    public FieldTooLongException(String message) {
        super(message);
    }
}