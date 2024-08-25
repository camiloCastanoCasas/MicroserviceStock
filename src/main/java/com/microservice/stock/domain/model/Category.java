package com.microservice.stock.domain.model;

import com.microservice.stock.domain.exceptions.FieldTooLongException;
import com.microservice.stock.domain.util.DomainConstants;
import com.microservice.stock.domain.exceptions.EmptyFieldException;

public class Category {

    private Long id;
    private String name;
    private String description;

    public Category(Long id, String name, String description) {
        if (name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }
        if(name.length() > DomainConstants.FIELD_NAME_CATEGORY_SIZE_MAX) {
            throw new FieldTooLongException(DomainConstants.FIELD_NAME_CATEGORY_SIZE_MESSAGE);
        }
        if(description.isEmpty()) {
            throw new EmptyFieldException(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        }
        if(description.length() > DomainConstants.FIELD_DESCRIPTION_CATEGORY_SIZE_MAX) {
            throw new FieldTooLongException(DomainConstants.FIELD_DESCRIPTION_CATEGORY_SIZE_MESSAGE);
        }
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
