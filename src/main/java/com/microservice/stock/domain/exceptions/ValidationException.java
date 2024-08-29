package com.microservice.stock.domain.exceptions;

import java.util.ArrayList;

public class ValidationException extends RuntimeException {
    private final ArrayList<String> errors;

    public ValidationException(ArrayList<String> errors) {
        super();
        this.errors = errors;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
