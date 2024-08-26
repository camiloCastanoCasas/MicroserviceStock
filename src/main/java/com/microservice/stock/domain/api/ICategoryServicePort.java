package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Category;

public interface ICategoryServicePort {
    void createCategory(Category category);
}
