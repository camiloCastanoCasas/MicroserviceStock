package com.microservice.stock.domain.model;

//import com.microservice.stock.domain.exceptions.EmptyFieldException;
//import com.microservice.stock.domain.exceptions.FieldTooLongException;
import com.microservice.stock.domain.util.DomainConstants;

public class Brand {
    private Long id;
    private String name;
    private String description;

    public Brand(Long id, String name, String description) {
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
