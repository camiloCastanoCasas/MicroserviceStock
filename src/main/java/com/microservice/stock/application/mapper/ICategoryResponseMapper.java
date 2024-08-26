package com.microservice.stock.application.mapper;

import com.microservice.stock.application.dto.response.CategoryResponse;
import com.microservice.stock.domain.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {

    CategoryResponse toCategoryResponse(Category category);

}
