package com.microservice.stock.infraestructure.out.jpa.mapper;

import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.infraestructure.out.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandEntityMapper {
    BrandEntity toEntity(Brand brand);
}
