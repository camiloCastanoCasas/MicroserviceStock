package com.microservice.stock.infraestructure.out.jpa.mapper;

import com.microservice.stock.domain.model.Category;
import com.microservice.stock.infraestructure.out.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {
    CategoryEntity toEntity(Category category);
}
