package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.CategoryRequest;
import com.microservice.stock.application.dto.response.CategoryResponse;
import com.microservice.stock.domain.util.Pagination;

public interface ICategoryHandler {
    void createCategory(CategoryRequest categoryRequest);
    Pagination<CategoryResponse> listCategories(int pageNumber, int pageSize, String sortby, String sortDirection);

}

