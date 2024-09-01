package com.microservice.stock.domain.api;

import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.Pagination;

public interface ICategoryServicePort {
    void createCategory(Category category);
    Pagination<Category> listCategory(int pageNumber, int pageSize, String sortby, String sortDirection);
}
