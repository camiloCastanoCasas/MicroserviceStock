package com.microservice.stock.domain.spi;

import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.Pagination;

public interface ICategoryPersistencePort {
    void createCategory(Category category);
    boolean existsByName(String name);
    boolean existById(Long id);
    Pagination<Category> listCategory(int pageNumber, int pageSize, String sortBy, String sortDirection);
}
