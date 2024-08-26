package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.CategoryRequest;

public interface ICategoryHandler {
    void createCategory(CategoryRequest CategoryRequest);
}

