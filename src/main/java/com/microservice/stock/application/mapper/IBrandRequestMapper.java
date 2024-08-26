package com.microservice.stock.application.mapper;

import com.microservice.stock.application.dto.request.BrandRequest;
import com.microservice.stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBrandRequestMapper {
    @Mapping(target = "id", ignore = true)
    Brand toBrand(BrandRequest BrandRequest);
}