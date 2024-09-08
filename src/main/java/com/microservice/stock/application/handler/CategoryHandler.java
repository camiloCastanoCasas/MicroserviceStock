package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.CategoryRequest;
import com.microservice.stock.application.dto.response.CategoryResponse;
import com.microservice.stock.application.dto.response.PaginationResponse;
import com.microservice.stock.application.mapper.ICategoryRequestMapper;
import com.microservice.stock.application.mapper.ICategoryResponseMapper;
import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.Pagination;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.createCategory(category);
    }

    @Override
    public PaginationResponse<CategoryResponse> listCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        // Retrieve the category pagination from the service
        Pagination<Category> categoryPagination = categoryServicePort.listCategory(pageNumber, pageSize, sortBy, sortDirection);

        // Convert categories to DTOs
        List<CategoryResponse> categoryResponses = categoryPagination.getContent().stream()
                .map(categoryResponseMapper::toCategoryResponse)
                .toList();

        // Create the Pagination object for the response
        Pagination<CategoryResponse> responsePagination = new Pagination<>(
                categoryResponses,
                categoryPagination.getPageNumber(),
                categoryPagination.getPageSize(),
                categoryPagination.getTotalElements()
        );
        return categoryResponseMapper.toPaginationResponse(responsePagination);
    }
}
