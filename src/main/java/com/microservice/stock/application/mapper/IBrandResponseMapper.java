package com.microservice.stock.application.mapper;

import com.microservice.stock.application.dto.response.BrandResponse;
import com.microservice.stock.application.dto.response.PaginationResponse;
import com.microservice.stock.domain.model.Brand;
import com.microservice.stock.domain.util.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBrandResponseMapper {
    BrandResponse toBrandResponse(Brand brand);

    @Mapping(target = "page", source = "pagination.pageNumber")
    @Mapping(target = "size", source = "pagination.pageSize")
    @Mapping(target = "totalElements", source = "pagination.totalElements")
    @Mapping(target = "totalPages", source = "pagination.totalPages")
    @Mapping(target = "first", source = "pagination.first")
    @Mapping(target = "last", source = "pagination.last")
    @Mapping(target = "empty", source = "pagination.empty")
    @Mapping(target = "content", source = "pagination.content")
    PaginationResponse<BrandResponse> toPaginationResponse(Pagination<BrandResponse> pagination);
}
