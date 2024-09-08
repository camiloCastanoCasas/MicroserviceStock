package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.CategoryRequest;
import com.microservice.stock.application.dto.response.CategoryResponse;
import com.microservice.stock.application.dto.response.PaginationResponse;

public interface ICategoryHandler {
    void createCategory(CategoryRequest categoryRequest);
    PaginationResponse<CategoryResponse> listCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

}

