package com.microservice.stock.application.mapper;

import com.microservice.stock.application.dto.request.CategoryRequest;
import com.microservice.stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryRequestMapper {

    @Mapping(target = "id", ignore = true)
    Category toCategory(CategoryRequest createCategoryRequest);
}
