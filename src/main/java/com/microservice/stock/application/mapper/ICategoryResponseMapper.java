package com.microservice.stock.application.mapper;

import com.microservice.stock.application.dto.response.CategoryResponse;
import com.microservice.stock.application.dto.response.PaginationResponse;
import com.microservice.stock.domain.model.Category;
import com.microservice.stock.domain.util.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {

    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "page", source = "pagination.pageNumber")
    @Mapping(target = "size", source = "pagination.pageSize")
    @Mapping(target = "totalElements", source = "pagination.totalElements")
    @Mapping(target = "totalPages", source = "pagination.totalPages")
    @Mapping(target = "first", source = "pagination.first")
    @Mapping(target = "last", source = "pagination.last")
    @Mapping(target = "empty", source = "pagination.empty")
    @Mapping(target = "content", source = "pagination.content")
    PaginationResponse<CategoryResponse> toPaginationResponse(Pagination<CategoryResponse> pagination);

}
