package com.microservice.stock.infraestructure.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message){
        super(message);
    }
}
