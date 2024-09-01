package com.microservice.stock.infraestructure.out.jpa.mapper;

import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.Pagination;
import com.microservice.stock.infraestructure.out.jpa.entity.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CategoryPageMapper {

    private final CategoryEntityMapper categoryEntityMapper;

    public Pagination<Category> toPagination(Page<CategoryEntity> page) {
        Pagination<Category> pagination = new Pagination<>();
        pagination.setContent(page.getContent().stream()
                .map(categoryEntityMapper::toDomain)
                .collect(Collectors.toList()));
        pagination.setPageNumber(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        return pagination;
    }
}
