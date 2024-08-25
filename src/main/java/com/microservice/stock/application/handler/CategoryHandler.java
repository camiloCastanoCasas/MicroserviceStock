package com.microservice.stock.application.handler;

import com.microservice.stock.application.dto.request.CategoryRequest;
import com.microservice.stock.application.mapper.ICategoryRequestMapper;
import com.microservice.stock.domain.api.ICategoryServicePort;
import com.microservice.stock.domain.model.Category;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
