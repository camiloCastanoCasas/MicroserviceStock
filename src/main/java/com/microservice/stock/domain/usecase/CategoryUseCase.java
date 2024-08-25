package com.microservice.stock.domain.usecase;

import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.spi.ICategoryPersistencePort;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort){
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createCategory(Category category) {
        categoryPersistencePort.createCategory(category);
    }
}
