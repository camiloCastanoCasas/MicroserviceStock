package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.CategoryRequest;
import com.microservice.stock.application.dto.response.CategoryResponse;
import com.microservice.stock.application.dto.response.PaginationResponse;

public interface ICategoryHandler {
    void createCategory(CategoryRequest categoryRequest);
    PaginationResponse<CategoryResponse> listCategories(int pageNumber, int pageSize, String sortBy, String sortDirection);

}

