package com.microservice.stock.application.mapper;

import com.microservice.stock.application.dto.response.BrandResponse;
import com.microservice.stock.domain.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBrandResponseMapper {
    BrandResponse toBrandResponse(Brand brand);
}
