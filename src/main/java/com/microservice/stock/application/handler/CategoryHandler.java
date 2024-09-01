package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.CategoryRequest;
import com.microservice.stock.application.dto.response.CategoryResponse;
import com.microservice.stock.application.mapper.ICategoryRequestMapper;
import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.Pagination;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        Category category = categoryRequestMapper.toCategory(categoryRequest);
        categoryServicePort.createCategory(category);
    }

    @Override
    public Pagination<CategoryResponse> listCategories(int pageNumber, int pageSize, String sortby, String sortDirection) {
        // Obtener la paginación de categorías del servicio
        Pagination<Category> categoryPagination = categoryServicePort.listCategory(pageNumber, pageSize, sortby, sortDirection);

        // Convertir las categorías a DTOs
        List<CategoryResponse> categoryResponses = categoryPagination.getContent().stream()
                .map(category -> categoryRequestMapper.toCategoryResponse(category))
                .collect(Collectors.toList());

        // Crear el objeto Pagination para la respuesta
        Pagination<CategoryResponse> responsePagination = new Pagination<>(
                categoryResponses,
                categoryPagination.getPageNumber(),
                categoryPagination.getPageSize(),
                categoryPagination.getTotalElements()
        );

        return responsePagination;
    }
}
