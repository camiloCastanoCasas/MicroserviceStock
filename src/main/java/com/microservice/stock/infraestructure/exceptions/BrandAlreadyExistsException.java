package com.microservice.stock.infraestructure.exceptions;

public class BrandAlreadyExistsException extends RuntimeException{
    public BrandAlreadyExistsException(String message){
        super(message);
    }
}
