package com.microservice.stock.infraestructure.exceptionhandler;

import com.microservice.stock.domain.exceptions.EmptyFieldException;
import com.microservice.stock.domain.exceptions.FieldTooLongException;
import com.microservice.stock.infraestructure.exceptions.BrandAlreadyExistsException;
import com.microservice.stock.infraestructure.exceptions.CategoryAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<Map<String,String>> handleEmptyFieldException(EmptyFieldException e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap(MESSAGE, e.getMessage()));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap(MESSAGE, e.getMessage()));
    }

    @ExceptionHandler(FieldTooLongException.class)
    public ResponseEntity<Map<String,String>> handleFieldTooLongException(FieldTooLongException e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap(MESSAGE, e.getMessage()));
    }

    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleBrandAlreadyExistsException(BrandAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap(MESSAGE, e.getMessage()));
    }
}
