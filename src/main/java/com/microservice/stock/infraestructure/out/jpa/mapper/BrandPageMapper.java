package com.microservice.stock.infraestructure.out.jpa.mapper;

import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.Pagination;
import com.microservice.stock.infraestructure.out.jpa.entity.BrandEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class BrandPageMapper {

    private final BrandEntityMapper brandEntityMapper;

    public Pagination<Brand> toPagination(Page<BrandEntity> page) {
        Pagination<Brand> pagination = new Pagination<>();
        pagination.setContent(page.getContent().stream()
                .map(brandEntityMapper::toDomain)
                .toList());
        pagination.setPageNumber(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        return pagination;
    }
}
